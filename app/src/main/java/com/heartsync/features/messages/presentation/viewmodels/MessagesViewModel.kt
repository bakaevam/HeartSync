package com.heartsync.features.messages.presentation.viewmodels

import com.heartsync.core.base.MviViewModel

class MessagesViewModel :
    MviViewModel<MessagesState, MessagesEffect, MessagesAction>(MessagesState()) {

    override fun onAction(action: MessagesAction) {

    }
}