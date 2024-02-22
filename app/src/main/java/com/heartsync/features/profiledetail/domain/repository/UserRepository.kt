package com.heartsync.features.profiledetail.domain.repository

import com.heartsync.features.cabinet.domain.model.ProfileData
import java.time.LocalDate

interface UserRepository {

    suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
        gender: String,
    )

    suspend fun getProfileData(): ProfileData?
}