package com.heartsync.features.main.data.repositories

import android.Manifest
import com.heartsync.features.main.data.providers.PermissionsProvider
import com.heartsync.features.main.domain.repositories.PermissionRepository

class PermissionRepositoryImpl(
    private val permissionsProvider: PermissionsProvider,
) : PermissionRepository {

    override fun checkPermission(permission: String): Boolean {
        return when (permission) {
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                permissionsProvider.checkWriteExternalStoragePermission()
            }

            else -> permissionsProvider.checkPermission(permission)
        }
    }
}