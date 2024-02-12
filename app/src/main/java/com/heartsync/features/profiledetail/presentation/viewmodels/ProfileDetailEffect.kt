package com.heartsync.features.profiledetail.presentation.viewmodels

import com.heartsync.core.base.Effect

sealed interface ProfileDetailEffect : Effect {

    class ShowError(
        val message: String? = null,
    ) : ProfileDetailEffect

    class ShowBirthdayPicker(
        val year: Int,
        val month: Int,
        val day: Int,
        val maxDay: Long,
    ) : ProfileDetailEffect
}