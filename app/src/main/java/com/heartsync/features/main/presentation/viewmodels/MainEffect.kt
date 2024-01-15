package com.heartsync.features.main.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface MainEffect : Effect {

    class ShowError(
        val message: String? = null,
    ) : MainEffect
}