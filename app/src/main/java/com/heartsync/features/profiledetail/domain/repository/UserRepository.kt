package com.heartsync.features.profiledetail.domain.repository

import java.time.LocalDate

interface UserRepository {

    suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
    )
}