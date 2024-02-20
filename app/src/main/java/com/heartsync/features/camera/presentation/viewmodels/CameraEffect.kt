package com.heartsync.features.camera.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface CameraEffect : Effect {

    object OpenAppSettings : CameraEffect

    object OpenGallery : CameraEffect

    class ShowMessage(
        val message: String? = null,
    ) : CameraEffect

    class RequestPermission(
        val permissions: Array<String>,
    ) : CameraEffect
}