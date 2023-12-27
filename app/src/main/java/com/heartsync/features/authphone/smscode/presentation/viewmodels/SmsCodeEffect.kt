package com.heartsync.features.authphone.smscode.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface SmsCodeEffect : Effect {

    class SendSmsCode(
        val phone: String,
    ) : SmsCodeEffect
}