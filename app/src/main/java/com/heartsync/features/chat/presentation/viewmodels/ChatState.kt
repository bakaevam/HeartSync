package com.heartsync.features.chat.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class ChatState(
    val channelId: String = EMPTY_STRING,
) : State