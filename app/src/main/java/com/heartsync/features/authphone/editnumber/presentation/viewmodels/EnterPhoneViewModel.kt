package com.heartsync.features.authphone.editnumber.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.tools.validators.PhoneValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EnterPhoneViewModel(
    private val appNavigator: AppNavigator,
) : MviViewModel<EnterPhoneState, EnterPhoneEffect, EnterPhoneAction>(EnterPhoneState()) {

    private val phoneFlow = MutableStateFlow(EMPTY_STRING)

    init {
        phoneFlow
            .onEach { phone ->
                setState { copy(phone = phone) }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(action: EnterPhoneAction) = when (action) {
        is EnterPhoneAction.OnContinueClick -> onContinueClick()
        is EnterPhoneAction.OnPhoneChange -> onPhoneChange(action)
        is EnterPhoneAction.OnBackClick -> appNavigator.tryNavigateBack()
    }

    private fun onContinueClick() {
        appNavigator.tryNavigateTo(
            route = Destination.SmsCodeScreen(
                phone = phoneFlow.value,
            )
        )
    }

    private fun onPhoneChange(action: EnterPhoneAction.OnPhoneChange) {
        phoneFlow.value = PhoneValidator.validate(action.phone)
        validate()
    }

    private fun validate() {
        setState { copy(continueEnabled = PhoneValidator.isValid(phoneFlow.value)) }
    }
}