package com.heartsync.features.main.data.store

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.heartsync.features.main.data.models.db.DbUserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseDatabase {

    private val database = Firebase.database

    suspend fun createUserInfo(
        dbUserInfo: DbUserInfo,
        userUid: String,
    ) {
        database
            .getReference(REFERENCE_USER_INFO)
            .child(userUid)
            .setValue(dbUserInfo)
            .await()
    }

    suspend fun updateUserInfo(
        updates: Map<String, Any>,
        userUid: String,
    ) {
        database
            .getReference(REFERENCE_USER_INFO)
            .child(userUid)
            .updateChildren(updates)
            .await()
    }

    suspend fun getUserInfo(userUid: String): DbUserInfo? =
        withContext(Dispatchers.Default) {
            database
                .getReference(REFERENCE_USER_INFO)
                .child(userUid)
                .get()
                .await()
                .getValue(DbUserInfo::class.java)
        }

    suspend fun getAllUsers(): List<DbUserInfo> =
        withContext(Dispatchers.Default) {
            database
                .getReference(REFERENCE_USER_INFO)
                .get()
                .await()
                .children
                .mapNotNull { it.getValue(DbUserInfo::class.java) }
        }

    private companion object {

        private const val REFERENCE_USER_INFO = "user_info"
    }
}