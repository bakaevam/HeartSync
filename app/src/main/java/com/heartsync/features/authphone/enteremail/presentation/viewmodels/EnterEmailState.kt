package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class EnterEmailState(
    val loading: Boolean = false,
    val email: String = EMPTY_STRING,
    val continueEnabled: Boolean = false,
) : State