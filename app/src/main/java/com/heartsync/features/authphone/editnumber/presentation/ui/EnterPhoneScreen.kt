package com.heartsync.features.authphone.editnumber.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterPhoneScreen(
    viewModel: EnterPhoneViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    EnterPhoneBody(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = viewModel::onAction,
    )
}