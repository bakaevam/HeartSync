package com.heartsync.features.chat.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heartsync.features.chat.presentation.viewmodels.ChatAction
import com.heartsync.features.chat.presentation.viewmodels.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            ChatHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 25.dp),
                name = state.name,
                image = state.image,
                online = state.online,
                onBackClick = { viewModel.onAction(ChatAction.OnBackClick) },
            )
        }
    ) { paddingValues ->
        ChatBody(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onAction = viewModel::onAction,
        )
    }
}