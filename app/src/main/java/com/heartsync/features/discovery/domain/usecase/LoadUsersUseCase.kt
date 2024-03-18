package com.heartsync.features.discovery.domain.usecase

import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.profiledetail.domain.repository.UserRepository

class LoadUsersUseCase(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): List<ProfileData> =
        userRepository.getAllUsers()
}