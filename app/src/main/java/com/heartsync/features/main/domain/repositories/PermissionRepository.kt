package com.heartsync.features.main.domain.repositories

interface PermissionRepository {

    fun checkPermission(permission: String): Boolean
}