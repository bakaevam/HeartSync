package com.heartsync.features.authphone.enteremail.presentation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.showToast
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailEffect
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterEmailScreen(
    viewModel: EnterEmailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    EnterEmailBody(
        state = state,
        onAction = viewModel::onAction,
    )

    val activity = LocalContext.current as Activity
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is EnterEmailEffect.ShowError -> activity.showToast(effect.message)
        }
    }
}