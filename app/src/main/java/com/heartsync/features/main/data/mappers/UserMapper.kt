package com.heartsync.features.main.data.mappers

import android.net.Uri
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.core.tools.format.DateTimeFormatter
import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.main.data.models.db.DbUserInfo
import com.heartsync.features.profiledetail.domain.UserInfo

object UserMapper {

    fun toDbUserInfo(userInfo: UserInfo): DbUserInfo =
        DbUserInfo(
            uid = userInfo.uid,
            id = userInfo.id,
            imageUrl = null,
            name = userInfo.name,
            lastName = userInfo.lastname,
            birthday = userInfo.birthday?.let { DateMapper.formatDayMonthYear(it) },
            profession = null,
            about = null,
            gender = userInfo.gender,
        )

    fun toProfileData(dbUserInfo: DbUserInfo): ProfileData =
        ProfileData(
            id = dbUserInfo.id ?: EMPTY_STRING,
            uid = dbUserInfo.uid ?: EMPTY_STRING,
            name = dbUserInfo.name ?: EMPTY_STRING,
            lastname = dbUserInfo.lastName ?: EMPTY_STRING,
            birthday = dbUserInfo.birthday?.let(DateTimeFormatter::toLocalDate),
            avatar = Uri.EMPTY,
        )
}