package com.heartsync.features.camera.presentation.viewmodels

import android.net.Uri
import com.heartsync.core.base.Action

sealed interface CameraAction : Action {

    object OnChangeLensClick : CameraAction

    object OnTakePictureClick : CameraAction

    object OnBackClick : CameraAction

    object OnPermissionClick : CameraAction

    object OnResume : CameraAction

    object OnGalleryClick : CameraAction

    class PermissionsResult(
        val permissions: Map<String, Boolean>,
    ) : CameraAction

    class OnPhotoGalleryChoose(
        val photo: Uri?,
    ) : CameraAction
}