package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.heartsync.R
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.TextProvider
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.validators.PasswordValidator
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EnterEmailViewModel(
    private val appNavigator: AppNavigator,
    private val enterEmailRepository: EnterEmailRepository,
    private val textProvider: TextProvider,
) : MviViewModel<EnterEmailState, EnterEmailEffect, EnterEmailAction>(
    EnterEmailState()
) {

    private val emailFlow = MutableStateFlow(EMPTY_STRING)
    private val passwordFlow = MutableStateFlow(EMPTY_STRING)
    private val repeatPasswordFlow = MutableStateFlow(EMPTY_STRING)

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
                enterEmailRepository.signUpWithPassword(emailFlow.value, passwordFlow.value)
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.e(TAG, "Email had registered", e)
                postEffect(EnterEmailEffect.ShowError(textProvider.getString(R.string.error_email_exist)))
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to sign up by email", e)
                postEffect(EnterEmailEffect.ShowError())
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun validate() {
        setState {
            copy(
                continueEnabled = emailFlow.value.isNotEmpty()
                    && PasswordValidator.newPasswordValidate(
                    password = passwordFlow.value,
                    repeatPassword = repeatPasswordFlow.value,
                ),
            )
        }
    }

    private companion object {
        private const val TAG = "Enter Email"
    }
}