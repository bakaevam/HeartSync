package com.heartsync.core.providers

import android.content.Context
import com.heartsync.BuildConfig
import com.heartsync.core.tools.EMPTY_STRING
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

    val client = MutableStateFlow<ChatClient?>(null)

    private val logLevel = if (BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING

    suspend fun initialize(
        userUid: String,
        nickname: String,
        avatar: String,
    ) {
        client.value = ChatClient.Builder(
            apiKey = BuildConfig.CHAT_API_KEY,
            appContext = context,
        )
            .withPlugins(statePluginFactory)
            .logLevel(logLevel)
            .build()
        if (ChatClient.instance().getCurrentUser() == null) {
            val user = createUser(userUid, nickname, avatar)

            client.value
                ?.connectUser(user = user, token = client.value?.devToken(userUid) ?: EMPTY_STRING)
                ?.await()
        }
    }

    fun getClientState(): ClientState? =
        client.value?.clientState

    suspend fun createChannel(
        members: List<String>,
    ): io.getstream.chat.android.models.Channel? {
        return client.value?.createChannel(
            channelType = CHANNEL_TYPE,
            memberIds = members,
            channelId = EMPTY_STRING,
            extraData = emptyMap(),
        )?.await()
            ?.getOrNull()
    }

    private fun createUser(
        userUid: String,
        nickname: String,
        avatar: String,
    ): User {
        return User(
            id = userUid,
            name = nickname,
            image = avatar,
        )
    }

    private companion object {
        private const val CHANNEL_TYPE = "messaging"
    }
}