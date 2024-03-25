package com.heartsync.features.messages.presentation.model

data class UiChatItem(
    val cid: String,
    val image: String,
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadMessages: Int?,
)