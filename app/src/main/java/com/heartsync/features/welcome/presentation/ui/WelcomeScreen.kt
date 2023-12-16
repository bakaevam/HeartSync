package com.heartsync.features.welcome.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.heartsync.core.ui.loader.ProgressBar
import com.heartsync.features.welcome.presentation.viewmodels.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        WelcomeBody(
            gallery = state.gallery,
            onAction = viewModel::onAction,
        )
        if (state.loading) {
            ProgressBar()
        }
    }
}