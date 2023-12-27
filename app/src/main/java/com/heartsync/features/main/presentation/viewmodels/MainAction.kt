package com.heartsync.features.main.presentation.viewmodels

import com.heartsync.core.base.Action
import com.heartsync.features.main.presentation.models.UiBottomItem

sealed interface MainAction : Action {

    object OnNavigateWelcome : MainAction

    object OnNavigateDiscovery : MainAction

    class OnNavItemClick(
        val bottomItem: UiBottomItem,
    ) : MainAction
}