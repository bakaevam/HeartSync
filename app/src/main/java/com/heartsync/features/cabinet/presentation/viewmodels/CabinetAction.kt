package com.heartsync.features.cabinet.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface CabinetAction : Action {

    object OnSignOutClick : CabinetAction
}