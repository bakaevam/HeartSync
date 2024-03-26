package com.heartsync.features.chat.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface ChatAction : Action {

    object OnBackClick : ChatAction

    object OnListEndReached : ChatAction

    object OnSendClick : ChatAction

    class OnTextChange(
        val text: String,
    ) : ChatAction
}