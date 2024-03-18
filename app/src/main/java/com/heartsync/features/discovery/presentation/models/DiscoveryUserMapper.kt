package com.heartsync.features.discovery.presentation.models

import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Period

object DiscoveryUserMapper : KoinComponent {

    private val dateTimeRepository by inject<DateTimeRepository>()

    fun toUiDiscoveryUser(
        user: ProfileData,
    ): UiDiscoveryUser {
        val today = dateTimeRepository.getCurrentDate()
        val age = Period.between(user.birthday, today).years
        return UiDiscoveryUser(
            id = user.id,
            uid = user.uid,
            imageUrl = EMPTY_STRING,
            nameAge = "${user.name}, $age",
            profession = EMPTY_STRING,
        )
    }
}