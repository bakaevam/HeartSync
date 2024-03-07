package com.heartsync.features.chat.presentation.viewmodels

import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator

class ChatViewModel(
    private val appNavigator: AppNavigator,
) : MviViewModel<ChatState, ChatEffect, ChatAction>(ChatState()) {

    override fun onAction(action: ChatAction) = when (action) {
        ChatAction.OnBackPress -> appNavigator.tryNavigateBack()
    }
}