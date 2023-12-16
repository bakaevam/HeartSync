package com.heartsync.features.welcome.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.features.welcome.presentation.models.UiWelcomePage

data class WelcomeState(
    val loading: Boolean = false,
    val gallery: List<UiWelcomePage> = emptyList(),
) : State