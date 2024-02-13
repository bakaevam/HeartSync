package com.heartsync.features.cabinet.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CabinetScreen(
    viewModel: CabinetViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    CabinetBody(
        state = state,
        onAction = viewModel::onAction,
    )
}