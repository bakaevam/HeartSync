package com.heartsync.features.cabinet.presentation.viewmodels

import com.heartsync.core.base.MviViewModel
import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase

class CabinetViewModel(
    private val signOutUseCase: SignOutUseCase,
) : MviViewModel<CabinetState, CabinetEffect, CabinetAction>(CabinetState()) {

    override fun onAction(action: CabinetAction) = when (action) {
        is CabinetAction.OnSignOutClick -> signOutUseCase()
    }
}