package com.heartsync.features.messages.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface MessagesAction : Action {

    class OnChannelClick(
        val channelId: String,
    ) : MessagesAction
}