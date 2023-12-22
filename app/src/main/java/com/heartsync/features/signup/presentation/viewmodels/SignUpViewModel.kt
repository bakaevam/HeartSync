package com.heartsync.features.signup.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.BASE_URL
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.signup.presentation.models.SocialSignUp
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val appNavigator: AppNavigator,
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : MviViewModel<SignUpState, SignUpEffect, SignUpAction>(
    SignUpState(
        socialSignUp = listOf(SocialSignUp.GOOGLE),
    )
) {

    override fun onAction(action: SignUpAction) = when (action) {
        is SignUpAction.OnSocialSignUpClick -> onSocialSignUpClick(action.socialSignUp)
        is SignUpAction.OnGetIdToken -> onGetIdToken(action)
        is SignUpAction.OnTermsOfUseClick -> postEffect(SignUpEffect.OpenWebPage(BASE_URL + TERMS_OF_USE))
        is SignUpAction.OnPrivacyPolicyClick -> postEffect(SignUpEffect.OpenWebPage(BASE_URL + PRIVACY_POLICY))
        is SignUpAction.OnContinueWithEmailClick -> {}
        is SignUpAction.OnUsePhoneClick -> onUsePhoneClick()
    }

    private fun onSocialSignUpClick(socialSignUp: SocialSignUp) {
        when (socialSignUp) {
            SocialSignUp.GOOGLE -> postEffect(SignUpEffect.SignUpViaGoogle(firebaseAuthProvider.getSignUpRequest()))
        }
    }

    private fun onGetIdToken(action: SignUpAction.OnGetIdToken) {
        viewModelScope.launch {
            try {
                val authResult = firebaseAuthProvider.signUpUser(action.idToken)
                Log.e(TAG, "Success sign up by social $authResult")
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to sign up by social", e)
            }
        }
    }

    private fun onUsePhoneClick() {
        appNavigator.tryNavigateTo(Destination.EnterPhone.fullRoute)
    }

    private companion object {
        private const val TAG = "Sign Up"
        private const val TERMS_OF_USE = "/terms_of_use.html"
        private const val PRIVACY_POLICY = "/privacy_policy.html"
    }
}