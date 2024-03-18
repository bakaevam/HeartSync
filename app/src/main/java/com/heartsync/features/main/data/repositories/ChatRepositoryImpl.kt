package com.heartsync.features.main.data.repositories

import com.heartsync.core.providers.ChatProvider
import com.heartsync.features.main.domain.repositories.ChatRepository
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import io.getstream.chat.android.models.Channel

class ChatRepositoryImpl(
    private val chatProvider: ChatProvider,
    private val userRepository: UserRepository,
) : ChatRepository {

    override suspend fun startChannel(userId: String): Channel? {
        val user = userRepository.getProfile()
        return if (user != null) {
            chatProvider.createChannel(
                members = listOf(userId, user.uid),
            )
        } else {
            null
        }
    }
}