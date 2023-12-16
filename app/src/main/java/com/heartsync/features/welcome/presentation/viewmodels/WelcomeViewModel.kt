package com.heartsync.features.welcome.presentation.viewmodels

import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator

class WelcomeViewModel(
    private val appNavigator: AppNavigator,
) : MviViewModel<WelcomeState, WelcomeEffect, WelcomeAction>(WelcomeState()) {

    override fun onAction(action: WelcomeAction) {

    }

}