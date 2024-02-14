package com.heartsync.features.main.data.store

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heartsync.features.main.domain.store.FirebaseStore
import com.heartsync.features.welcome.data.models.DbPhoto
import com.heartsync.features.welcome.data.models.PhotosMapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseStoreImpl : FirebaseStore {

    private val store: FirebaseFirestore = Firebase.firestore

    override fun getWelcomePhotos(): List<DbPhoto> =
        runBlocking {
            store
                .collection(TABLE_PHOTOS)
                .whereEqualTo(FIELD_PHOTO_TYPE, PHOTO_TYPE_WELCOME)
                .get()
                .await()
                .map(PhotosMapper::toDbPhoto)
                .toList()
        }

    private companion object {

        private const val TABLE_PHOTOS = "photos"
        private const val FIELD_PHOTO_TYPE = "type"
        private const val PHOTO_TYPE_WELCOME = "welcome"
    }
}