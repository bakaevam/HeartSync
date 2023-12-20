package com.heartsync.features.signup.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.signup.presentation.models.SocialSignUp
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : MviViewModel<SignUpState, SignUpEffect, SignUpAction>(
    SignUpState(
        socialSignUp = listOf(SocialSignUp.GOOGLE),
    )
) {

    override fun onAction(action: SignUpAction) = when (action) {
        is SignUpAction.OnSocialSignUpClick -> onSocialSignUpClick(action.socialSignUp)
        is SignUpAction.OnGetIdToken -> onGetIdToken(action)
        else -> {}
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

    private companion object {
        private const val TAG = "Sign Up"
    }
}