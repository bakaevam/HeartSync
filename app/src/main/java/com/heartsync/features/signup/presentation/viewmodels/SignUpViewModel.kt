package com.heartsync.features.signup.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.heartsync.R
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.TextProvider
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.BASE_URL
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.signup.presentation.models.SocialSignUp
import com.heartsync.features.welcome.domain.models.AuthScenario
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val appNavigator: AppNavigator,
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val savedStateHandle: SavedStateHandle,
    private val textProvider: TextProvider,
) : MviViewModel<SignUpState, SignUpEffect, SignUpAction>(
    SignUpState(
        socialSignUp = listOf(SocialSignUp.GOOGLE),
    )
) {

    private var params = Params()

    init {
        getSavedArguments()
        initTexts()
    }

    override fun onAction(action: SignUpAction) = when (action) {
        is SignUpAction.OnSocialSignUpClick -> onSocialSignUpClick(action.socialSignUp)
        is SignUpAction.OnGetIdToken -> onGetIdToken(action)
        is SignUpAction.OnTermsOfUseClick -> postEffect(SignUpEffect.OpenWebPage(BASE_URL + TERMS_OF_USE))
        is SignUpAction.OnPrivacyPolicyClick -> postEffect(SignUpEffect.OpenWebPage(BASE_URL + PRIVACY_POLICY))
        is SignUpAction.OnContinueWithEmailClick -> appNavigator.tryNavigateTo(
            Destination.EnterEmailScreen(
                params.authScenario ?: AuthScenario.SIGN_UP
            )
        )

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
        appNavigator.tryNavigateTo(Destination.EnterPhoneScreen.fullRoute)
    }

    private fun getSavedArguments() {
        val scenario = savedStateHandle.get<AuthScenario>(Destination.SignUpScreen.KEY_SCENARIO)
        params = params.copy(authScenario = scenario)
    }

    private fun initTexts() {
        setState {
            copy(
                title = when (params.authScenario) {
                    AuthScenario.SIGN_UP -> textProvider.getString(R.string.sign_up_continue)
                    else -> textProvider.getString(R.string.sign_up_in_continue)
                },
                socialTitle = when (params.authScenario) {
                    AuthScenario.SIGN_UP -> textProvider.getString(R.string.sign_up_social)
                    else -> textProvider.getString(R.string.sign_up_in_social)
                },
            )
        }
    }

    data class Params(
        val authScenario: AuthScenario? = null,
    )

    private companion object {
        private const val TAG = "Sign Up"
        private const val TERMS_OF_USE = "/terms_of_use.html"
        private const val PRIVACY_POLICY = "/privacy_policy.html"
    }
}