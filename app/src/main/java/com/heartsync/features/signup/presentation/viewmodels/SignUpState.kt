package com.heartsync.features.signup.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.signup.presentation.models.SocialSignUp

data class SignUpState(
    val title: String = EMPTY_STRING,
    val socialTitle: String = EMPTY_STRING,
    val socialSignUp: List<SocialSignUp> = emptyList(),
) : State