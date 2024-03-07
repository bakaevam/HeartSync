package com.heartsync.features.chat.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.heartsync.features.chat.presentation.viewmodels.ChatAction
import com.heartsync.features.chat.presentation.viewmodels.ChatViewModel
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ChatTheme {
        val context = LocalContext.current
        MessagesScreen(
            viewModelFactory = MessagesViewModelFactory(
                context = context,
                channelId = state.channelId,
            ),
            onBackPressed = { viewModel.onAction(ChatAction.OnBackPress) }
        )
    }
}