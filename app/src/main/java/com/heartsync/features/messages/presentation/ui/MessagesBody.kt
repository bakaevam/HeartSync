package com.heartsync.features.messages.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.extention.OnListEndReached
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.fields.SearchBar
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.skModernist
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.messages.presentation.model.UiChatItem
import com.heartsync.features.messages.presentation.viewmodels.MessagesAction
import com.heartsync.features.messages.presentation.viewmodels.MessagesState
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamTypography
import io.getstream.chat.android.models.InitializationState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        MessagesBody(
            state = MessagesState(
                channels = listOf(
                    UiChatItem(
                        image = "elit",
                        name = "Virgie Kerr",
                        lastMessage = "scripserit",
                        time = "23 min",
                        unreadMessages = 1,
                        cid = EMPTY_STRING,
                    ),
                    UiChatItem(
                        image = "elit",
                        name = "Virgie Kerr",
                        lastMessage = "scripserit",
                        time = "23 min",
                        unreadMessages = 1,
                        cid = EMPTY_STRING,
                    ),
                    UiChatItem(
                        image = "elit",
                        name = "Virgie Kerr",
                        lastMessage = "scripserit",
                        time = "23 min",
                        unreadMessages = 1,
                        cid = EMPTY_STRING,
                    ),
                ),
            ),
            onAction = {},
        )
    }
}

@Composable
fun MessagesBody(
    state: MessagesState,
    modifier: Modifier = Modifier,
    onAction: (MessagesAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = state.query,
            onQueryChange = { query -> onAction(MessagesAction.OnQueryChange(query)) },
            onClearClick = { onAction(MessagesAction.OnQueryClearClick) },
        )
        Spacer(Modifier.height(20.dp))
        val listState = rememberLazyListState()
        listState.OnListEndReached(
            buffer = 5,
            handler = { onAction(MessagesAction.OnListEndReached) },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            itemsIndexed(items = state.channels) { index, chat ->
                ChatItem(
                    chat = chat,
                    divider = index != state.channels.lastIndex,
                    onClick = { onAction(MessagesAction.OnChannelClick(chat.cid)) },
                )
            }
        }
        ChatTheme(
            typography = StreamTypography.defaultTypography(skModernist),
        ) {
            val initializationState by state.clientState.collectAsState()
            when (initializationState) {
                InitializationState.COMPLETE -> {
                    ChannelsScreen(
                        title = stringResource(R.string.messages_title),
                        isShowingSearch = true,
                        onItemClick = { channel ->
                            onAction(MessagesAction.OnChannelClick(channel.cid))
                        },
                    )
                }

                InitializationState.INITIALIZING -> {
                    AppText(text = "Initialising...")
                }

                else -> {
                    AppText(text = "Not initialized...")
                }
            }
        }
    }
}