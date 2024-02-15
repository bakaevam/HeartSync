package com.heartsync.features.camera.data.providers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraProvider(
    private val context: Context,
) {

    val imageCapture = ImageCapture
        .Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()

    private var cameraExecutor: ExecutorService? = null

    fun startCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun stopCamera() {
        cameraExecutor?.shutdown()
    }

    fun getCameraSelector(lensFacing: Int): CameraSelector =
        CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

    fun takePicture(
        outputOptions: ImageCapture.OutputFileOptions,
        photoFile: File,
        onImageCaptured: (Uri) -> Unit,
    ) {
        imageCapture.takePicture(
            outputOptions,
            cameraExecutor as Executor,
            getImageSaveCallback(
                photoFile = photoFile,
                onImageCaptured = onImageCaptured,
            ),
        )
    }

    private fun getImageSaveCallback(
        photoFile: File,
        onImageCaptured: (Uri) -> Unit,
    ): ImageCapture.OnImageSavedCallback {
        return object : ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "Take photo error:", exception)
                throw exception
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
                Log.e(TAG, "Photo capture succeeded: $savedUri")
            }
        }
    }

    private companion object {
        private const val TAG = "Camera Provider"
    }
}