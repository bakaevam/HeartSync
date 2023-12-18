package com.heartsync.features.welcome.domain.repositories

import com.heartsync.features.welcome.domain.models.WelcomePage

interface WelcomeRepository {

    suspend fun getWelcomePages(): List<WelcomePage>
}