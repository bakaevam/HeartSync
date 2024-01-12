package com.heartsync.features.authphone.enteremail.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface EnterEmailEffect : Effect {

    class ShowError(
        val message: String? = null,
    ) : EnterEmailEffect
}