package com.heartsync.features.authphone.editnumber.presentation.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.ui.loader.BlockingLoader
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneAction
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneEffect
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneViewModel
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import org.koin.androidx.compose.get
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
    if (state.loading) {
        BlockingLoader(Modifier.fillMaxSize())
    }

    val firebaseAuthProvider = get<FirebaseAuthProvider>()
    val activity = LocalContext.current as Activity
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is EnterPhoneEffect.SendSmsCode -> {
                firebaseAuthProvider.sendVerificationCode(
                    phoneNumber = effect.phone,
                    activity = activity,
                )
                viewModel.onAction(EnterPhoneAction.OnNavigateSmsCode)
            }

            else -> {}
        }
    }
}