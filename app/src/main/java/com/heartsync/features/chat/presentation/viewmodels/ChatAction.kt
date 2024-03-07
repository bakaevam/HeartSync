package com.heartsync.features.chat.presentation.viewmodels

import com.heartsync.core.base.Action

sealed interface ChatAction : Action {

    object OnBackPress : ChatAction
}