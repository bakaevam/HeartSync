package com.heartsync.features.camera.domain.repositories

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture

interface CameraRepository {

    fun takePicture(
        onImageCaptured: (Uri) -> Unit,
        filename: String? = null,
    )

    fun getImageCapture(): ImageCapture

    fun startCamera()

    fun stopCamera()

    fun getCameraSelector(lensFacing: Int): CameraSelector

    suspend fun uploadAvatar(uri: Uri)
}