package com.heartsync.features.authphone.editnumber.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface EnterPhoneAction : Action {

    object OnContinueClick : EnterPhoneAction

    object OnBackClick : EnterPhoneAction

    class OnPhoneChange(
        val phone: String,
    ) : EnterPhoneAction
}