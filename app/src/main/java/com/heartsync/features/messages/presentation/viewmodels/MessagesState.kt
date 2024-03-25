package com.heartsync.features.messages.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.messages.presentation.model.UiChatItem
import io.getstream.chat.android.models.InitializationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MessagesState(
    val clientState: StateFlow<InitializationState?> = MutableStateFlow(null),
    val query: String = EMPTY_STRING,
    val channels: List<UiChatItem> = emptyList(),
) : State