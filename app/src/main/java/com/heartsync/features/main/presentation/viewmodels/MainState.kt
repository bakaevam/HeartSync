package com.heartsync.features.main.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.features.main.presentation.models.UiBottomItem
import com.heartsync.features.main.presentation.models.UiNavItem

data class MainState(
    val bottomBarVisible: Boolean = false,
    val currentNavItem: UiBottomItem? = null,
    val bottomNavItems: List<UiNavItem> = emptyList(),
) : State