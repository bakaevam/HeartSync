package com.heartsync.features.chat.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.ChatProvider
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import com.heartsync.features.messages.presentation.model.ChannelsMapper
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.state.extensions.loadOlderMessages
import io.getstream.chat.android.state.extensions.watchChannelAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val appNavigator: AppNavigator,
    private val savedStateHandle: SavedStateHandle,
    private val chatProvider: ChatProvider,
    private val textProvider: TextProvider,
    private val dateTimeRepository: DateTimeRepository,
) : MviViewModel<ChatState, ChatEffect, ChatAction>(ChatState()) {

    private var channelTypeId: String = EMPTY_STRING
    private var channelId: String? = null
    private val channelsMapper = ChannelsMapper(
        textProvider = textProvider,
        dateTimeRepository = dateTimeRepository,
    )
    private val textFlow = MutableStateFlow<String>(EMPTY_STRING)

    init {
        getMessages()
        viewModelScope.launch {
            textFlow.collectLatest { text ->
                setState { copy(text = text) }
            }
        }
    }

    override fun onAction(action: ChatAction) = when (action) {
        is ChatAction.OnBackClick -> appNavigator.tryNavigateBack()
        is ChatAction.OnListEndReached -> loadMoreMessages()
        is ChatAction.OnSendClick -> onSendClick()
        is ChatAction.OnTextChange -> onTextChange(action)
    }

    private fun initArguments() {
        val cid = savedStateHandle.get<String>(Destination.ChatScreen.KEY_CHANNEL_ID)
        channelTypeId = cid ?: EMPTY_STRING
        setState { copy(channelId = this@ChatViewModel.channelTypeId) }
    }

    private fun getMessages() {
        initArguments()
        val chatClient = chatProvider.client.value
        val channelStateFlow = chatClient?.watchChannelAsState(
            cid = channelTypeId,
            messageLimit = 30,
            coroutineScope = viewModelScope,
        )

        viewModelScope.launch {
            channelStateFlow?.collect { channelState ->
                val channel = channelState?.toChannel()
                channelId = channel?.id
                val userId = chatClient.getCurrentUser()?.id
                if (channel != null && userId != null) {
                    setState {
                        copy(
                            name = channelsMapper.getName(channel, userId),
                            image = channel.image,
                        )
                    }
                    launch {
                        channelState.members.collect { members ->
                            val online = members.firstOrNull { it.user.id != userId }?.user?.online
                            setState { copy(online = online ?: false) }
                        }
                    }
                    launch {
                        channelState.messages.collect { messages ->
                            setState {
                                copy(messages = messages.map { message ->
                                    channelsMapper.toUiMessage(message, chatClient)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadMoreMessages() {
        val chatClient = chatProvider.client.value
        chatClient?.loadOlderMessages(cid = channelTypeId, messageLimit = 30)
            ?.enqueue()
    }

    private fun onTextChange(action: ChatAction.OnTextChange) {
        textFlow.value = action.text
    }

    private fun onSendClick() {
        viewModelScope.launch {
            try {
                val chatClient = chatProvider.client.value
                if (channelId != null) {
                    chatClient?.sendMessage(
                        channelType = "messaging",
                        channelId = channelId!!,
                        message = Message(text = textFlow.value),
                        isRetrying = false,
                    )?.await()
                    textFlow.value = EMPTY_STRING
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to send message", e)
            }
        }
    }

    private companion object {
        private const val TAG = "Chat ViewModel"
    }
}