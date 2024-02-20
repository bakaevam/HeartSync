package com.heartsync.features.cabinet.presentation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import com.heartsync.core.tools.extention.OnLifecycleEvent
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetAction
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CabinetScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: CabinetViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val activity = LocalContext.current as Activity
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                val avatar =
                    navBackStackEntry.savedStateHandle.get<String>(Destination.ProfileDetailScreen.KEY_AVATAR)
                viewModel.onAction(CabinetAction.OnResume(avatar))
            }

            else -> {}
        }
    }
    CabinetBody(
        state = state,
        onAction = viewModel::onAction,
    )
}