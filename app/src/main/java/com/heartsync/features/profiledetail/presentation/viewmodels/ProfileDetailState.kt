package com.heartsync.features.profiledetail.presentation.viewmodels

import com.heartsync.core.base.State
import com.heartsync.core.tools.EMPTY_STRING

data class ProfileDetailState(
    val loading: Boolean = false,
    val avatar: String? = null,
    val name: String = EMPTY_STRING,
    val lastName: String = EMPTY_STRING,
    val birthday: String = EMPTY_STRING,
) : State