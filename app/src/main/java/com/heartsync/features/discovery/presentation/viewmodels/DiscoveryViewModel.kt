package com.heartsync.features.discovery.presentation.viewmodels

import com.heartsync.core.base.MviViewModel

class DiscoveryViewModel : MviViewModel<DiscoveryState, DiscoveryEffect, DiscoveryAction>(
    DiscoveryState()
) {
    override fun onAction(action: DiscoveryAction) {

    }
}