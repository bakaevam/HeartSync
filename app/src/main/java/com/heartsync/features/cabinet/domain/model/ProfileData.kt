package com.heartsync.features.cabinet.domain.model

import android.net.Uri
import java.time.LocalDate

data class ProfileData(
    val avatar: Uri,
    val name: String,
    val lastname: String,
    val birthday: LocalDate?,
)