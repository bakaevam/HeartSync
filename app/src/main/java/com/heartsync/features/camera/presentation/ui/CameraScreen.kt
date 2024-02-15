package com.heartsync.features.camera.presentation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.showToast
import com.heartsync.features.camera.presentation.viewmodels.CameraEffect
import com.heartsync.features.camera.presentation.viewmodels.CameraViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Camera(
        state = state,
        onAction = viewModel::onAction,
    )
    val activity = LocalContext.current as Activity
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is CameraEffect.ShowMessage -> activity.showToast(effect.message)
        }
    }
}