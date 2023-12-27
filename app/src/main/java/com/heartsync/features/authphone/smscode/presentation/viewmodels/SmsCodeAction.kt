package com.heartsync.features.authphone.smscode.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface SmsCodeAction : Action {

    object OnResendCodeClick : SmsCodeAction

    object OnBackspaceClick : SmsCodeAction

    class OnKeyClick(
        val digit: Char,
    ) : SmsCodeAction
}