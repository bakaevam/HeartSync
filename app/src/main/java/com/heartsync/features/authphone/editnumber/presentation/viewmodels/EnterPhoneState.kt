package com.heartsync.features.authphone.editnumber.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class EnterPhoneState(
    val loading: Boolean = false,
    val phone: String = EMPTY_STRING,
    val continueEnabled: Boolean = false,
) : State