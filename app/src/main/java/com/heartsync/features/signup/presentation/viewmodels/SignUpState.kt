package com.heartsync.features.signup.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.features.signup.presentation.models.SocialSignUp

data class SignUpState(
    val socialSignUp: List<SocialSignUp> = emptyList(),
) : State