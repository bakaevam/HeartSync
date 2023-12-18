package com.heartsync.core.network.db

import com.heartsync.features.welcome.data.models.DbPhoto

interface FirebaseDatabase {

    fun getWelcomePhotos(): List<DbPhoto>
}