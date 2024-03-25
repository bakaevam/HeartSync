package com.heartsync.features.messages.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.ChatProvider
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import com.heartsync.features.messages.presentation.model.ChannelsMapper
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.InitializationState
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.state.extensions.queryChannelsAsState
import io.getstream.chat.android.state.plugin.state.querychannels.QueryChannelsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesViewModel(
    private val appNavigator: AppNavigator,
    private val userRepository: UserRepository,
    private val chatProvider: ChatProvider,
    private val textProvider: TextProvider,
    private val dateTimeRepository: DateTimeRepository,
) : MviViewModel<MessagesState, MessagesEffect, MessagesAction>(
    MessagesState(
        clientState = MutableStateFlow<InitializationState?>(null),
    )
) {

    private val channelsMapper = ChannelsMapper(
        textProvider = textProvider,
        dateTimeRepository = dateTimeRepository,
    )
    private val queryFlow: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)
    private var queryChannelsStateFlow: StateFlow<QueryChannelsState?> = MutableStateFlow(null)

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
        viewModelScope.launch {
            queryFlow.collect { query ->
                setState { copy(query = query) }
            }
        }
        loadChannels()
    }

    override fun onAction(action: MessagesAction) = when (action) {
        is MessagesAction.OnChannelClick -> onChannelClick(action)
        is MessagesAction.OnQueryChange -> onQueryChange(action)
        is MessagesAction.OnQueryClearClick -> onQueryClearClick()
        is MessagesAction.OnListEndReached -> loadMoreChannels()
    }

    private fun onChannelClick(action: MessagesAction.OnChannelClick) {
        appNavigator.tryNavigateTo(Destination.ChatScreen.invoke(action.channelId))
    }

    private fun onQueryClearClick() {
        queryFlow.value = EMPTY_STRING
    }

    private fun onQueryChange(action: MessagesAction.OnQueryChange) {
        queryFlow.value = action.query
    }

    private fun loadChannels() {
        viewModelScope.launch {
            val userId = userRepository.getUserUid() ?: EMPTY_STRING
            val request = QueryChannelsRequest(
                filter = Filters.and(
                    Filters.eq("type", "messaging"),
                    Filters.`in`("members", listOf(userId)),
                ),
                offset = 0,
                limit = 12,
                querySort = QuerySortByField.descByName("last_updated")
            )
            val chatClient = chatProvider.client.value
            if (chatClient != null) {
                queryChannelsStateFlow = chatClient.queryChannelsAsState(
                    request = request,
                    coroutineScope = viewModelScope
                )
                queryChannelsStateFlow.collect { queryChannelsState ->
                    if (queryChannelsState != null) {
                        queryChannelsState.channels.collect { channels ->
                            channels?.let {
                                setState {
                                    copy(
                                        channels = channels.map { channel ->
                                            channelsMapper.toUiChatItem(
                                                channel = channel,
                                                userId = userId,
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    } else {
                        // _uiState.update { it.copy(error = "Cannot load channels") }
                    }
                }
            }
        }
    }

    private fun loadMoreChannels() {
        viewModelScope.launch {
            val queryChannelsState = queryChannelsStateFlow.value ?: return@launch

            queryChannelsState.nextPageRequest.value?.let {
                val chatClient = chatProvider.client.value
                chatClient?.queryChannels(it)?.await()
            }
        }
    }

    private companion object {
        private const val TAG = "Messages ViewModel"
    }
}