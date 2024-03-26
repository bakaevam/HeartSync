package com.heartsync.features.messages.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface MessagesAction : Action {

    object OnQueryClearClick : MessagesAction

    object OnListEndReached : MessagesAction

    class OnChannelClick(
        val channelId: String,
    ) : MessagesAction

    class OnQueryChange(
        val query: String,
    ) : MessagesAction
}