package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface EnterEmailAction : Action {

    object OnBackClick : EnterEmailAction

    object OnContinueClick : EnterEmailAction

    class OnEmailChange(
        val email: String,
    ) : EnterEmailAction

    class OnPasswordChange(
        val password: String,
    ) : EnterEmailAction

    class OnRepeatPasswordChange(
        val repeatPassword: String,
    ) : EnterEmailAction
}