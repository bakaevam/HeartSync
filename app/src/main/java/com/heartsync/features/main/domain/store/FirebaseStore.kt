package com.heartsync.features.main.domain.store

import com.heartsync.features.welcome.data.models.DbPhoto

interface FirebaseStore {

    fun getWelcomePhotos(): List<DbPhoto>
}