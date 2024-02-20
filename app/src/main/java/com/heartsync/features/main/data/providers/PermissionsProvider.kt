package com.heartsync.features.main.data.providers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build

class PermissionsProvider(
    private val contextProvider: ContextProvider,
) {

    fun checkPermission(permission: String): Boolean =
        contextProvider.checkPermission(permission) == PackageManager.PERMISSION_GRANTED

    fun checkWriteExternalStoragePermission(): Boolean =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            contextProvider.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    fun checkReadMedia(): Boolean =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            contextProvider.checkPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
}