package com.heartsync.features.chat.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.chat.presentation.model.UiMessage

data class ChatState(
    val channelId: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val online: Boolean = false,
    val image: String = EMPTY_STRING,
    val messages: List<UiMessage> = emptyList(),
    val text: String = EMPTY_STRING,
) : State