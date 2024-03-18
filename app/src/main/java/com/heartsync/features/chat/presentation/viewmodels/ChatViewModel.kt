package com.heartsync.features.chat.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination

class ChatViewModel(
    private val appNavigator: AppNavigator,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<ChatState, ChatEffect, ChatAction>(ChatState()) {

    private var channelId: String = EMPTY_STRING

    init {
        initArguments()
    }

    override fun onAction(action: ChatAction) = when (action) {
        ChatAction.OnBackPress -> appNavigator.tryNavigateBack()
    }

    private fun initArguments() {
        val cid = savedStateHandle.get<String>(Destination.ChatScreen.KEY_CHANNEL_ID)
        channelId = cid ?: EMPTY_STRING
        setState { copy(channelId = this@ChatViewModel.channelId) }
    }
}