package com.heartsync.features.cabinet.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.features.cabinet.presentation.model.UiProfileData

data class CabinetState(
    val loading: Boolean = false,
    val uiProfileData: UiProfileData? = null,
) : State