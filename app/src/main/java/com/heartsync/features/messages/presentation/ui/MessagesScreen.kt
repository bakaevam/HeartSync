package com.heartsync.features.messages.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.heartsync.features.messages.presentation.viewmodels.MessagesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val initializationState by state.clientState.collectAsState()
        MessagesBody(
            state = initializationState,
            onAction = viewModel::onAction,
        )
    }
}