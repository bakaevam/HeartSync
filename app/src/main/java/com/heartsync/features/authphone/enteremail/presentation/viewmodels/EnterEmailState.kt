package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class EnterEmailState(
    val description: String = EMPTY_STRING,
    val loading: Boolean = false,
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val repeatPasswordVisible: Boolean = false,
    val repeatPassword: String = EMPTY_STRING,
    val continueEnabled: Boolean = false,
) : State