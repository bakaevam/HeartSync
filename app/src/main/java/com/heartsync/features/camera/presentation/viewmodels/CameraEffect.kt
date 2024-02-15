package com.heartsync.features.camera.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface CameraEffect : Effect {

    class ShowMessage(
        val message: String? = null,
    ) : CameraEffect
}