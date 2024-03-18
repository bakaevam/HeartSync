package com.heartsync.features.camera.data.repositories

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import com.heartsync.features.camera.data.providers.CameraProvider
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.main.data.providers.FileProvider
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.StorageSourceImpl.Companion.FILENAME_AVATAR
import com.heartsync.features.main.domain.store.StorageSource
import java.io.File

class CameraRepositoryImpl(
    private val fileProvider: FileProvider,
    private val cameraProvider: CameraProvider,
    private val storageSource: StorageSource,
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : CameraRepository {

    override fun takePicture(
        onImageCaptured: (Uri) -> Unit,
        filename: String?,
    ) {
        // Output directory to save captured image
        val outputDirectory = fileProvider.createPhotoFile()

        // Time stamped name and MediaStore entry
        val photoFile = File(
            outputDirectory,
            fileProvider.createNewFileName(
                fileName = filename ?: NAME_PHOTO,
                suffix = PHOTO_SUFFIX,
                withDateTime = false,
            )
        )
        // Output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has been taken
        cameraProvider.takePicture(
            outputOptions = outputOptions,
            photoFile = photoFile,
            onImageCaptured = onImageCaptured,
        )
    }

    override fun getImageCapture(): ImageCapture =
        cameraProvider.imageCapture

    override fun startCamera() {
        cameraProvider.startCamera()
    }

    override fun stopCamera() {
        cameraProvider.stopCamera()
    }

    override fun getCameraSelector(lensFacing: Int): CameraSelector =
        cameraProvider.getCameraSelector(lensFacing)

    override suspend fun uploadAvatar(uri: Uri) {
        val userUid = firebaseAuthProvider.getCurrentUser()?.uid
        if (userUid != null) {
            storageSource.loadPhoto(
                uri = uri,
                userId = userUid,
                filename = "$FILENAME_AVATAR.$PHOTO_SUFFIX"
            )
        }
    }

    private companion object {
        private const val NAME_PHOTO = "HeartSync"
        private const val PHOTO_SUFFIX = "jpg"
    }
}