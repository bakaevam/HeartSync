package com.heartsync.core.network.db.mappers

import com.heartsync.core.network.db.model.DbUserInfo
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.format.DateFormatter
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.profiledetail.domain.UserInfo
import java.time.LocalDate

object UserMapper {

    fun toDbUserInfo(userInfo: UserInfo): DbUserInfo =
        DbUserInfo(
            imageUrl = null,
            name = userInfo.name,
            lastName = userInfo.lastname,
            birthday = DateMapper.formatDayMonthYear(userInfo.birthday),
            profession = null,
            about = null
        )

    fun createDbUser(
        imageUrl: String? = null,
        name: String? = null,
        lastName: String? = null,
        birthday: LocalDate? = null,
        profession: String? = null,
        about: String? = null,
    ): DbUserInfo =
        DbUserInfo(
            imageUrl = imageUrl,
            name = name,
            lastName = lastName,
            birthday = DateMapper.formatDayMonthYear(birthday),
            profession = profession,
            about = about,
        )

    fun toProfileData(dbUserInfo: DbUserInfo): ProfileData =
        ProfileData(
            name = dbUserInfo.name ?: EMPTY_STRING,
            lastname = dbUserInfo.lastName ?: EMPTY_STRING,
            birthday = dbUserInfo.birthday?.let(DateFormatter::toLocalDate),
        )
}