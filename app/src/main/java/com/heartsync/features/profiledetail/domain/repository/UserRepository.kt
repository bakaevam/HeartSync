package com.heartsync.features.profiledetail.domain.repository

import android.net.Uri
import com.heartsync.features.cabinet.domain.model.ProfileData
import io.getstream.chat.android.client.setup.state.ClientState
import java.time.LocalDate

interface UserRepository {

    suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
        gender: String?,
    )

    suspend fun getProfileData(): ProfileData?

    suspend fun getProfile(): ProfileData?

    fun getClientState(): ClientState?

    suspend fun initChats()

    suspend fun getAllUsers(): List<ProfileData>

    suspend fun getUserUid(): String?

    suspend fun getAvatarByUid(userUid: String): Uri?
}