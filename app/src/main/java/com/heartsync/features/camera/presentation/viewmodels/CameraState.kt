package com.heartsync.features.camera.presentation.viewmodels

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import com.heartsync.core.base.State

data class CameraState(
    val cameraProvider: ProcessCameraProvider? = null,
    val imageCapture: ImageCapture? = null,
    val cameraSelector: CameraSelector? = null,
) : State