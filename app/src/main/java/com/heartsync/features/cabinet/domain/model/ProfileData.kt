package com.heartsync.features.cabinet.domain.model

import java.time.LocalDate

data class ProfileData(
    val name: String,
    val lastname: String,
    val birthday: LocalDate?,
)