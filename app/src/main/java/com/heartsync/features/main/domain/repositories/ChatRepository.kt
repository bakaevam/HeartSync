package com.heartsync.features.main.domain.repositories

import io.getstream.chat.android.models.Channel

interface ChatRepository {

    suspend fun startChannel(
        userId: String,
    ): Channel?
}