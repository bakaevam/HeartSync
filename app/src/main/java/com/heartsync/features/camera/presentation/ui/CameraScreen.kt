package com.heartsync.features.camera.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import com.heartsync.R
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.OnLifecycleEvent
import com.heartsync.core.tools.extention.openAppSettings
import com.heartsync.core.tools.extention.showToast
import com.heartsync.core.ui.InfoBody
import com.heartsync.features.camera.presentation.viewmodels.CameraAction
import com.heartsync.features.camera.presentation.viewmodels.CameraEffect
import com.heartsync.features.camera.presentation.viewmodels.CameraViewModel
import com.heartsync.features.main.presentation.ui.MainActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    when {
        state.permissionDescriptionVisible -> {
            InfoBody(
                title = stringResource(R.string.camera_permission_title),
                message = stringResource(R.string.camera_permission_message),
                button = stringResource(R.string.camera_permission_button),
                onClick = { viewModel.onAction(CameraAction.OnPermissionClick) },
                onBackClick = { viewModel.onAction(CameraAction.OnBackClick) }
            )
        }

        state.cameraVisible -> {
            Camera(
                state = state,
                onAction = viewModel::onAction,
            )
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        viewModel.onAction(CameraAction.PermissionsResult(permissions))
    }
    val activity = LocalContext.current as MainActivity
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onAction(CameraAction.OnResume)
            }

            else -> {}
        }
    }
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is CameraEffect.ShowMessage -> activity.showToast(effect.message)
            is CameraEffect.RequestPermission -> launcher.launch(effect.permissions)
            is CameraEffect.OpenAppSettings -> activity.openAppSettings()
        }
    }
}