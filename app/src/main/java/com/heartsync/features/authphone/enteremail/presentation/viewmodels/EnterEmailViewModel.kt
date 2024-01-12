package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EnterEmailViewModel(
    private val appNavigator: AppNavigator,
    private val enterEmailRepository: EnterEmailRepository,
) : MviViewModel<EnterEmailState, EnterEmailEffect, EnterEmailAction>(
    EnterEmailState()
) {

    private val emailFlow = MutableStateFlow(EMPTY_STRING)

    init {
        emailFlow
            .onEach { email ->
                setState { copy(email = email) }
                validate()
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(action: EnterEmailAction) = when (action) {
        is EnterEmailAction.OnBackClick -> appNavigator.tryNavigateBack()
        is EnterEmailAction.OnContinueClick -> onContinueClick()
        is EnterEmailAction.OnEmailChange -> onEmailChange(action)
    }

    private fun onEmailChange(action: EnterEmailAction.OnEmailChange) {
        emailFlow.value = action.email
    }

    private fun onContinueClick() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                enterEmailRepository.sendEmailLink(emailFlow.value)
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to send Email link", e)
                postEffect(EnterEmailEffect.ShowError())
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun validate() {
        setState {
            copy(
                continueEnabled = emailFlow.value.isNotEmpty(),
            )
        }
    }

    private companion object {
        private const val TAG = "Enter Email"
    }
}