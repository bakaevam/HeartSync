package com.heartsync.features.authphone.smscode.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class SmsCodeState(
    val loading: Boolean = false,
    val phone: String = EMPTY_STRING,
    val timer: String? = null,
    val code: List<Char> = emptyList(),
    val maxLength: Int,
    val resendCodeEnabled: Boolean = false,
    val backspaceEnabled: Boolean = false,
) : State