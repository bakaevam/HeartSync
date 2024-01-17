package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.heartsync.R
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.TextProvider
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.tools.validators.PasswordValidator
import com.heartsync.features.signup.domain.AuthRepository
import com.heartsync.features.welcome.domain.models.AuthScenario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EnterEmailViewModel(
    private val appNavigator: AppNavigator,
    private val authRepository: AuthRepository,
    private val textProvider: TextProvider,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<EnterEmailState, EnterEmailEffect, EnterEmailAction>(
    EnterEmailState()
) {

    private val emailFlow = MutableStateFlow(EMPTY_STRING)
    private val passwordFlow = MutableStateFlow(EMPTY_STRING)
    private val repeatPasswordFlow = MutableStateFlow(EMPTY_STRING)

    private var params = Params()

    init {
        emailFlow
            .onEach { email ->
                setState { copy(email = email) }
                validate()
            }
            .launchIn(viewModelScope)
        passwordFlow
            .onEach { password ->
                setState { copy(password = password) }
                validate()
            }
            .launchIn(viewModelScope)
        repeatPasswordFlow
            .onEach { repeatPassword ->
                setState { copy(repeatPassword = repeatPassword) }
                validate()
            }
            .launchIn(viewModelScope)

        getSavedArguments()
        initTexts()
    }

    override fun onAction(action: EnterEmailAction) = when (action) {
        is EnterEmailAction.OnBackClick -> appNavigator.tryNavigateBack()
        is EnterEmailAction.OnContinueClick -> onContinueClick()
        is EnterEmailAction.OnEmailChange -> onEmailChange(action)
        is EnterEmailAction.OnPasswordChange -> onPasswordChange(action)
        is EnterEmailAction.OnRepeatPasswordChange -> onRepeatPasswordChange(action)
    }

    private fun onEmailChange(action: EnterEmailAction.OnEmailChange) {
        emailFlow.value = action.email
    }

    private fun onPasswordChange(action: EnterEmailAction.OnPasswordChange) {
        passwordFlow.value = action.password
    }

    private fun onRepeatPasswordChange(action: EnterEmailAction.OnRepeatPasswordChange) {
        repeatPasswordFlow.value = action.repeatPassword
    }

    private fun onContinueClick() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                when (params.authScenario) {
                    AuthScenario.SIGN_UP -> {
                        authRepository.singUpByEmailPassword(emailFlow.value, passwordFlow.value)
                    }

                    AuthScenario.SIGN_IN -> {
                        authRepository.signInByEmailPassword(emailFlow.value, passwordFlow.value)
                    }

                    null -> {}
                }
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.e(TAG, "Email had registered", e)
                postEffect(EnterEmailEffect.ShowError(textProvider.getString(R.string.error_email_exist)))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e(TAG, "Bad credentials", e)
                postEffect(EnterEmailEffect.ShowError(textProvider.getString(R.string.error_bad_credentials)))
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to sign up by email", e)
                postEffect(EnterEmailEffect.ShowError())
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun validate() {
        val passwordValid = if (params.authScenario == AuthScenario.SIGN_UP) {
            PasswordValidator.newPasswordValidate(
                password = passwordFlow.value,
                repeatPassword = repeatPasswordFlow.value,
            )
        } else {
            passwordFlow.value.isNotEmpty()
        }
        setState {
            copy(
                continueEnabled = emailFlow.value.isNotEmpty() && passwordValid,
            )
        }
    }

    private fun getSavedArguments() {
        val scenario = savedStateHandle.get<AuthScenario>(Destination.SignUpScreen.KEY_SCENARIO)
        params = params.copy(authScenario = scenario)
    }

    private fun initTexts() {
        setState {
            copy(
                description = when (params.authScenario) {
                    AuthScenario.SIGN_UP -> textProvider.getString(R.string.enter_email_description_sign_up)
                    else -> textProvider.getString(R.string.enter_email_description_sign_in)
                },
                repeatPasswordVisible = params.authScenario == AuthScenario.SIGN_UP,
            )
        }
    }

    data class Params(
        val authScenario: AuthScenario? = null,
    )

    private companion object {
        private const val TAG = "Enter Email"
    }
}