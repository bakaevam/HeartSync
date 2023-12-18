package com.heartsync.core.network.db

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heartsync.features.welcome.data.models.DbPhoto
import com.heartsync.features.welcome.data.models.PhotosMapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseDatabaseImpl : FirebaseDatabase {

    private val database: FirebaseFirestore = Firebase.firestore

    override fun getWelcomePhotos(): List<DbPhoto> =
        runBlocking {
            database
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