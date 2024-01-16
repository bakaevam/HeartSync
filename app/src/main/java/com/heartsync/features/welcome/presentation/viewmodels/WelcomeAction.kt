package com.heartsync.features.welcome.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface WelcomeAction : Action {

    object OnCreateAccountClick : WelcomeAction

    object OnSignInCLick : WelcomeAction
}