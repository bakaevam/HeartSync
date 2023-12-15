package com.heartsync.features.main.presentation.viewmodels

import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator

class MainViewModel(
    appNavigator: AppNavigator,
) : MviViewModel<MainState, MainEffect, MainAction>(MainState()) {

    val navigationChannel = appNavigator.navigationChannel

    override fun onAction(action: MainAction) {

    }
}