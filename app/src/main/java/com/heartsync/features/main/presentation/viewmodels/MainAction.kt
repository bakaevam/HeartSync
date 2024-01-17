package com.heartsync.features.main.presentation.viewmodels

import android.net.Uri
import com.heartsync.core.base.Action
import com.heartsync.features.main.presentation.models.UiBottomItem

sealed interface MainAction : Action {

    object OnNavigateWelcome : MainAction

    object OnNavigateProfileDetail : MainAction

    object OnNavigateDiscovery : MainAction

    class OnHandleDeeplink(
        val deeplink: Uri?,
    ) : MainAction

    class OnNavItemClick(
        val bottomItem: UiBottomItem,
    ) : MainAction
}