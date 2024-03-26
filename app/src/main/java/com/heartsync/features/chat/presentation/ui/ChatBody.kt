package com.heartsync.features.chat.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.extention.OnListEndReached
import com.heartsync.core.tools.extention.condition
import com.heartsync.features.chat.presentation.viewmodels.ChatAction
import com.heartsync.features.chat.presentation.viewmodels.ChatState

@Composable
fun ChatBody(
    state: ChatState,
    modifier: Modifier = Modifier,
    onAction: (ChatAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
    ) {
        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = state.messages.lastIndex,
        )
        listState.OnListEndReached(buffer = 5, handler = { onAction(ChatAction.OnListEndReached) })
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            contentPadding = PaddingValues(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items = state.messages) { message ->
                if (message.text.isNotEmpty()) {
                    MessageListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .condition(
                                condition = message.isMine,
                                then = { align(Alignment.End) },
                            ),
                        message = message,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MessagesComposer(
            modifier = Modifier.padding(horizontal = 40.dp),
            text = state.text,
            onTextChange = { text -> onAction(ChatAction.OnTextChange(text)) },
            onSendClick = { onAction(ChatAction.OnSendClick) },
        )
    }
}