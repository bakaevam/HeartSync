package com.heartsync.features.profiledetail.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDetailScreen(
    viewModel: ProfileDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    ProfileDetailBody(
        state = state,
        onAction = viewModel::onAction,
    )
}