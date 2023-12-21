package com.heartsync.features.signup.presentation.viewmodels

import com.heartsync.core.base.Action
import com.heartsync.features.signup.presentation.models.SocialSignUp

interface SignUpAction : Action {

    object OnContinueWithEmailClick : SignUpAction

    object OnUsePhoneClick : SignUpAction

    object OnTermsOfUseClick : SignUpAction

    object OnPrivacyPolicyClick : SignUpAction

    class OnSocialSignUpClick(
        val socialSignUp: SocialSignUp,
    ) : SignUpAction

    class OnGetIdToken(
        val idToken: String,
    ) : SignUpAction
}