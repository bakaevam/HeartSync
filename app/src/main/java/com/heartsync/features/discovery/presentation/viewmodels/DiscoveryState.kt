package com.heartsync.features.discovery.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.features.discovery.presentation.models.UiDiscoveryUser

data class DiscoveryState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val people: List<UiDiscoveryUser> = emptyList(),
) : State