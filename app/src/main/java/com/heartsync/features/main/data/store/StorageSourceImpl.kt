package com.heartsync.features.main.data.store

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.heartsync.features.main.domain.store.StorageSource
import kotlinx.coroutines.tasks.await

class StorageSourceImpl : StorageSource {

    private val storage = Firebase.storage

    override suspend fun loadPhoto(
        uri: Uri,
        userId: String,
        filename: String?,
    ) {
        val file = filename ?: uri.lastPathSegment
        val reference = storage.reference.child("$PATH_AVATARS$userId/$file")
        reference.putFile(uri).await()
    }

    override suspend fun getAvatar(userId: String): Uri {
        val reference =
            storage.reference.child("$PATH_AVATARS$userId/$FILENAME_AVATAR$AVATAR_EXTENSION")
        return reference.downloadUrl.await()
    }

    companion object {
        private const val PATH_AVATARS = "avatars/"
        private const val AVATAR_EXTENSION = ".jpg"
        const val FILENAME_AVATAR = "avatar"
    }
}