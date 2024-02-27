package com.heartsync.features.messages.presentation.viewmodels

import com.heartsync.core.base.State
import io.getstream.chat.android.models.InitializationState
import kotlinx.coroutines.flow.StateFlow

data class MessagesState(
    val clientState: StateFlow<InitializationState?>,
) : State