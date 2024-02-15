package com.heartsync.features.camera.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface CameraAction : Action {

    object OnChangeLensClick : CameraAction

    object OnTakePictureClick : CameraAction

    object OnBackClick : CameraAction

    object OnPermissionClick : CameraAction

    object OnResume : CameraAction

    class PermissionsResult(
        val permissions: Map<String, Boolean>,
    ) : CameraAction
}