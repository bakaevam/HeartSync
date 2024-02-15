package com.heartsync.features.main.data.repositories

import com.heartsync.features.main.domain.repositories.DateTimeRepository
import java.time.LocalDateTime

class DateTimeRepositoryImpl : DateTimeRepository {

    override fun getCurrentDateTime(): LocalDateTime =
        LocalDateTime.now()
}