package com.heartsync.features.chat.presentation.model

data class UiMessage(
    val isMine: Boolean,
    val text: String,
    val time: String,
    val read: Boolean,
)