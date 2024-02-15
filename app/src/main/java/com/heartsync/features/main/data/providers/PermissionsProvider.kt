package com.heartsync.features.main.data.providers

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class PermissionsProvider(
    private val contextProvider: ContextProvider,
    private val context: Context,
) {

    fun checkPermission(permission: String): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            contextProvider.checkPermission(permission) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

}