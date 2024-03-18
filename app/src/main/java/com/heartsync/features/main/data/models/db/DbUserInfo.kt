package com.heartsync.features.main.data.models.db

import com.heartsync.core.tools.EMPTY_STRING

/**
 * birthday - Формат даты xx.xx.xxxx
 */
data class DbUserInfo(
    val uid: String? = null,
    val id: String? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val birthday: String? = null,
    val profession: String? = null,
    val about: String? = null,
    val gender: String = EMPTY_STRING,
)