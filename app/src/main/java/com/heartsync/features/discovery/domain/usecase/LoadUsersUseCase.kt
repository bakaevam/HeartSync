package com.heartsync.features.discovery.domain.usecase

import android.net.Uri
import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.profiledetail.domain.repository.UserRepository

class LoadUsersUseCase(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): List<ProfileData> {
        val users = userRepository.getAllUsers()
        return users.map { profileData ->
            profileData.copy(
                avatar = userRepository.getAvatarByUid(profileData.uid) ?: Uri.EMPTY,
            )
        }
    }
}