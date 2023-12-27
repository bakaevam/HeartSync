package com.heartsync.features.authphone.smscode.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.heartsync.features.authphone.smscode.presentation.viewmodels.SmsCodeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SmsCodeScreen(
    viewModel: SmsCodeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    SmsCodeBody(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = viewModel::onAction,
    )
}