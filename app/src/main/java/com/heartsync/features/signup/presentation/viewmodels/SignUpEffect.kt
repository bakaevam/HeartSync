package com.heartsync.features.signup.presentation.viewmodels

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.heartsync.core.base.Effect

interface SignUpEffect : Effect {

    class SignUpViaGoogle(
        val oneTapRequest: BeginSignInRequest,
    ) : SignUpEffect

    class OpenWebPage(
        val url: String,
    ) : SignUpEffect
}