package com.heartsync.core.network.store

import com.heartsync.features.welcome.data.models.DbPhoto

interface FirebaseStore {

    fun getWelcomePhotos(): List<DbPhoto>
}