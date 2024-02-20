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

    fun createNewFileName(
        fileName: String,
        suffix: String,
        withDateTime: Boolean = true,
    ): String {
        val currentDateTime = dateTimeRepository.getCurrentDateTime()
        return buildString {
            append(fileName)
            if (withDateTime) {
                val dateTime = DateFormatter.formatFileDateTime(currentDateTime)
                append('-')
                append(dateTime)
            }
            append('.')
            val fileExtension = fileName.substringAfterLast(DELIMITER, suffix)
            append(fileExtension)
        }
    }

    private companion object {
        private const val DELIMITER = '.'
        private const val CAMERA_DIRECTORY = "Camera"
    }
}