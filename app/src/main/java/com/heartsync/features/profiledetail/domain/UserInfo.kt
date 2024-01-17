package com.heartsync.features.profiledetail.domain

import java.time.LocalDate

data class UserInfo(
    val id: String,
    val name: String,
    val lastname: String,
    val birthday: LocalDate?,
)
