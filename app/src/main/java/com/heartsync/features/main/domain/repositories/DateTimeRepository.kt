package com.heartsync.features.main.domain.repositories

import java.time.LocalDateTime

interface DateTimeRepository {

    fun getCurrentDateTime(): LocalDateTime
}