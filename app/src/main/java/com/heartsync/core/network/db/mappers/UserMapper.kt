package com.heartsync.core.network.db.mappers

import com.heartsync.core.network.db.model.DbUserInfo
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.profiledetail.domain.UserInfo

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
}