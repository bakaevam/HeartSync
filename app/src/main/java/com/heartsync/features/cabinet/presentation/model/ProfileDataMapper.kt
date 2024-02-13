package com.heartsync.features.cabinet.presentation.model

import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.format.DateFormatter
import com.heartsync.features.cabinet.domain.model.ProfileData

object ProfileDataMapper {

    fun toUiProfileData(data: ProfileData): UiProfileData =
        UiProfileData(
            name = data.name,
            lastName = data.lastname,
            birthday = data.birthday?.let(DateFormatter::formatDateString) ?: EMPTY_STRING,
        )
}