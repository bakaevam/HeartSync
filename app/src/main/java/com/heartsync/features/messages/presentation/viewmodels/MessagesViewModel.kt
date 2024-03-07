package com.heartsync.features.messages.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import io.getstream.chat.android.models.InitializationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessagesViewModel(
    private val appNavigator: AppNavigator,
    private val userRepository: UserRepository,
) : MviViewModel<MessagesState, MessagesEffect, MessagesAction>(
    MessagesState(
        clientState = MutableStateFlow<InitializationState?>(null),
    )
) {

    init {
        viewModelScope.launch {
            try {
                val clientState = userRepository.getClientState()
                if (clientState != null) {
                    setState { copy(clientState = clientState.initializationState) }
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to connect user", e)
            }
        }
    }

    override fun onAction(action: MessagesAction) = when (action) {
        is MessagesAction.OnChannelClick -> onChannelClick(action)
    }

    private fun onChannelClick(action: MessagesAction.OnChannelClick) {
        appNavigator.tryNavigateTo(Destination.ChatScreen(action.channelId))
    }

    private companion object {
        private const val TAG = "Messages ViewModel"
    }
}