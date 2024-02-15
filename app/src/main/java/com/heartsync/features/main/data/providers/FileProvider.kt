package com.heartsync.features.main.data.providers

import android.os.Environment
import com.heartsync.core.tools.format.DateFormatter
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import java.io.File

class FileProvider(
    private val dateTimeRepository: DateTimeRepository,
) {

    fun createPhotoFile(): File =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            CAMERA_DIRECTORY,
        )

    fun createNewFileName(fileName: String, suffix: String): String {
        val currentDateTime = dateTimeRepository.getCurrentDateTime()
        val dateTime = DateFormatter.formatFileDateTime(currentDateTime)
        val fileExtension = fileName.substringAfterLast(DELIMITER, suffix)
        val baseFilename = fileName.substringBeforeLast(DELIMITER)
        return "$baseFilename-$dateTime.$fileExtension"
    }

    private companion object {
        private const val DELIMITER = '.'
        private const val CAMERA_DIRECTORY = "Camera"
    }
}