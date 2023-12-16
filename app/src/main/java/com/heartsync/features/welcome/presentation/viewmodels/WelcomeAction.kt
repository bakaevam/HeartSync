package com.heartsync.features.welcome.presentation.viewmodels

import com.heartsync.core.base.Action

interface WelcomeAction : Action {

    object OnCreateAccountClick : WelcomeAction

    object OnSignInCLick : WelcomeAction
}