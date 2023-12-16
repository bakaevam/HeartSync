package com.heartsync.features.main.presentation.viewmodels

import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route

class MainViewModel(
    appNavigator: AppNavigator,
) : MviViewModel<MainState, MainEffect, MainAction>(MainState()) {

    val navigationChannel = appNavigator.navigationChannel

    init {
        val authorization = false
        if (authorization) {

        } else {
            appNavigator.tryNavigateTo(
                route = Route.WELCOME.key,
                popUpToRoute = null,
                inclusive = false,
                isSingleTop = false,
            )
        }
    }

    override fun onAction(action: MainAction) {

    }
}