package com.heartsync.features.main.domain.store

import android.net.Uri

interface StorageSource {

    suspend fun loadPhoto(
        uri: Uri,
        userId: String,
    )

    suspend fun getAvatar(userId: String): Uri
}