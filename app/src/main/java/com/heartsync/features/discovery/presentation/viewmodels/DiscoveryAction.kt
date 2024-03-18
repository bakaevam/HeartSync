package com.heartsync.features.discovery.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface DiscoveryAction : Action {

    object OnFilterClick : DiscoveryAction

    class OnMessageClick(
        val userId: String,
    ) : DiscoveryAction
}