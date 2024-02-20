package com.heartsync.features.profiledetail.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface ProfileDetailAction : Action {

    object OnSkipClick : ProfileDetailAction

    object OnSaveClick : ProfileDetailAction

    object OnEditAvatarClick : ProfileDetailAction

    object OnBirthdayClick : ProfileDetailAction

    class OnResume(
        val avatar: String? = null,
    ) : ProfileDetailAction

    class OnNameChange(
        val name: String,
    ) : ProfileDetailAction

    class OnLastnameChange(
        val lastname: String,
    ) : ProfileDetailAction

    class OnBirthdayConfirm(
        val year: Int,
        val month: Int,
        val day: Int,
    ) : ProfileDetailAction
}