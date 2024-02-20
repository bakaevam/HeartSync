package com.heartsync.features.camera.presentation.viewmodels

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.viewModelScope
import com.heartsync.R
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.data.store.StorageSourceImpl
import com.heartsync.features.main.domain.repositories.PermissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CameraViewModel(
    private val appNavigator: AppNavigator,
    private val cameraRepository: CameraRepository,
    private val textProvider: TextProvider,
    private val permissionRepository: PermissionRepository,
) : MviViewModel<CameraState, CameraEffect, CameraAction>(CameraState()) {

    private val lensFacing = MutableStateFlow<Int>(CameraSelector.LENS_FACING_BACK)
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
    )
    private val avatarUriState = MutableStateFlow<Uri?>(null)

    init {
        checkPermissions()
        lensFacing
            .onEach { lens ->
                setState { copy(cameraSelector = cameraRepository.getCameraSelector(lens)) }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        cameraRepository.stopCamera()
    }

    override fun onAction(action: CameraAction) = when (action) {
        is CameraAction.OnChangeLensClick -> changeCameraLens()
        is CameraAction.OnTakePictureClick -> onTakePictureClick()
        is CameraAction.OnBackClick -> pressBack()
        is CameraAction.OnPermissionClick -> onCameraPermissionClick()
        is CameraAction.PermissionsResult -> handlePermissionsResult(action)
        is CameraAction.OnResume -> checkPermissions()
    }

    private fun handlePermissionsResult(action: CameraAction.PermissionsResult) {
        val permissions = action.permissions
        if (permissions.isNotEmpty() && permissions.all { it.value }) {
            startCamera()
            setState { copy(cameraVisible = true) }
        } else {
            setState { copy(permissionDescriptionVisible = true) }
        }
    }

    private fun onCameraPermissionClick() {
        postEffect(CameraEffect.OpenAppSettings)
    }

    private fun checkPermissions() {
        when {
            permissions.all { permission ->
                val granted = permissionRepository.checkPermission(permission)
                Log.e(TAG, "$permission = $granted")
                granted
            } -> {
                startCamera()
                setState {
                    copy(
                        permissionDescriptionVisible = false,
                        cameraVisible = true,
                    )
                }
            }

            else -> postEffect(CameraEffect.RequestPermission(permissions))
        }
    }

    private fun startCamera() {
        viewModelScope.launch {
            setState {
                copy(
                    imageCapture = cameraRepository.getImageCapture(),
                )
            }
            cameraRepository.startCamera()
        }
    }

    private fun pressBack() {
        appNavigator.tryNavigateBack()
    }

    private fun onTakePictureClick() {
        viewModelScope.launch {
            try {
                cameraRepository.takePicture(
                    filename = StorageSourceImpl.FILENAME_AVATAR,
                    onImageCaptured = { uri ->
                        postEffect(CameraEffect.ShowMessage(textProvider.getString(R.string.camera_take_picture_success)))
                        appNavigator.tryNavigateBack(
                            data = mapOf(Destination.ProfileDetailScreen.KEY_AVATAR to uri.toString()),
                        )
                    }
                )
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to take a picture")
                postEffect(CameraEffect.ShowMessage())
            }
        }
    }

    private fun changeCameraLens() {
        lensFacing.value = when (lensFacing.value) {
            CameraSelector.LENS_FACING_BACK -> CameraSelector.LENS_FACING_FRONT
            else -> CameraSelector.LENS_FACING_BACK
        }
    }

    private companion object {
        private const val TAG = "Camera ViewModel"
    }
}