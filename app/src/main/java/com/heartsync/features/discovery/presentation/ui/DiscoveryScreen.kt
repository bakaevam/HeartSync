package com.heartsync.features.discovery.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DiscoveryScreen(
    viewModel: DiscoveryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    DiscoveryBody(
        state = state,
        onAction = viewModel::onAction,
    )
}