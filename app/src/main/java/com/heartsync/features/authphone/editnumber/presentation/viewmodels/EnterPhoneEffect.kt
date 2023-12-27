package com.heartsync.features.authphone.editnumber.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface EnterPhoneEffect : Effect {

    class SendSmsCode(
        val phone: String,
    ) : EnterPhoneEffect
}