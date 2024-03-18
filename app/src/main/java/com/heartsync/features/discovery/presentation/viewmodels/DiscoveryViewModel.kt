package com.heartsync.features.discovery.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.discovery.domain.usecase.LoadUsersUseCase
import com.heartsync.features.discovery.presentation.models.DiscoveryUserMapper
import com.heartsync.features.main.domain.repositories.ChatRepository
import kotlinx.coroutines.launch

class DiscoveryViewModel(
    private val appNavigator: AppNavigator,
    private val loadUsers: LoadUsersUseCase,
    private val chatRepository: ChatRepository,
) : MviViewModel<DiscoveryState, DiscoveryEffect, DiscoveryAction>(
    DiscoveryState()
) {

    init {
        loadDiscoveryUsers()
    }

    override fun onAction(action: DiscoveryAction) = when (action) {
        is DiscoveryAction.OnFilterClick -> {}
        is DiscoveryAction.OnMessageClick -> onMessageClick(action)
    }

    private fun onMessageClick(action: DiscoveryAction.OnMessageClick) {
        viewModelScope.launch {
            try {
                val userId = action.userId
                val channel = chatRepository.startChannel(userId)
                if (channel?.cid != null) {
                    appNavigator.navigateTo(Destination.ChatScreen.invoke(channel.cid))
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to start channel", e)
            }
        }
    }

    private fun loadDiscoveryUsers() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true, error = false) }
                val users = loadUsers()
                    .map(DiscoveryUserMapper::toUiDiscoveryUser)
                setState { copy(people = users) }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to load discovery users", e)
                setState { copy(error = true) }
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private companion object {
        private const val TAG = "Discovery View Model"
    }
}