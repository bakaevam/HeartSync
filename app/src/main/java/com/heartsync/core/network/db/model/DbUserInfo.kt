package com.heartsync.core.network.db.model

/**
 * birthday - Формат даты xx.xx.xxxx
 */
data class DbUserInfo(
    val imageUrl: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val birthday: String? = null,
    val profession: String? = null,
    val about: String? = null,
)