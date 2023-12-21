package com.heartsync.features.signup.presentation.ui

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.heartsync.R
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.showToast
import com.heartsync.features.signup.presentation.viewmodels.SignUpAction
import com.heartsync.features.signup.presentation.viewmodels.SignUpEffect
import com.heartsync.features.signup.presentation.viewmodels.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SignUp Screen"

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val activity = LocalContext.current as Activity

    SignUpBody(
        state = state,
        onAction = viewModel::onAction,
    )

    val oneTapClient = Identity.getSignInClient(activity)
    val googleError = stringResource(R.string.error_google_account)
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            viewModel.onAction(SignUpAction.OnGetIdToken(idToken))
                            Log.d(TAG, "Got ID token.")
                        }

                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d(TAG, "Got password.")
                        }

                        else -> {
                            Log.d(TAG, "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    activity.showToast(googleError)
                    e.localizedMessage?.let { Log.d(TAG, it) }
                }
            }
        }

    val uriHandler = LocalUriHandler.current
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is SignUpEffect.SignUpViaGoogle -> {
                oneTapClient.beginSignIn(effect.oneTapRequest)
                    .addOnSuccessListener(activity) { result ->
                        try {
                            launcher.launch(
                                IntentSenderRequest
                                    .Builder(result.pendingIntent.intentSender)
                                    .build()
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    .addOnFailureListener(activity) { e ->
                        activity.showToast(googleError)
                        e.localizedMessage?.let { Log.d(TAG, it) }
                    }
            }

            is SignUpEffect.OpenWebPage -> {
                uriHandler.openUri(effect.url)
            }

            else -> {}
        }
    }
}