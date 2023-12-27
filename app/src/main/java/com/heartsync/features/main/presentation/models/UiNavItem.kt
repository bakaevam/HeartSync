package com.heartsync.features.main.presentation.models

data class UiNavItem(
    val bottomItem: UiBottomItem,
    val selected: Boolean,
    val badgeText: String?,
)