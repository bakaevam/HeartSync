package com.heartsync.core.providers

import android.content.Context
import com.heartsync.BuildConfig
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.setup.state.ClientState
import io.getstream.chat.android.models.User
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import kotlinx.coroutines.flow.MutableStateFlow

class ChatProvider(
    private val context: Context,
) {

    private val statePluginFactory = StreamStatePluginFactory(
        config = StatePluginConfig(
            backgroundSyncEnabled = true,
            userPresence = true,
        ),
        appContext = context,
    )

    private val client = MutableStateFlow<ChatClient?>(null)

    val logLevel = if (BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING

    init {
        client.value = ChatClient.Builder(
            apiKey = BuildConfig.CHAT_API_KEY,
            appContext = context,
        )
            .withPlugins(statePluginFactory)
            .logLevel(logLevel)
            .build()
    }

    suspend fun initialize(
        userUid: String,
        nickname: String,
        token: String,
    ) {
        client.value = ChatClient.Builder(
            apiKey = BuildConfig.CHAT_API_KEY,
            appContext = context,
        )
            .withPlugins(statePluginFactory)
            .logLevel(logLevel)
            .build()
        if (client.value?.getCurrentUser() == null) {
            val user = createUser(userUid, nickname)
            client.value
                ?.connectUser(user = user, token = token)
                ?.await()
        }
    }

    fun getClientState(): ClientState? =
        client.value?.clientState

    private fun createUser(
        userUid: String,
        nickname: String,
    ): User {
        return User(
            id = userUid,
            name = nickname,
        )
    }
}