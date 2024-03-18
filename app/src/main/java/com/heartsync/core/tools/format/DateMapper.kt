package com.heartsync.core.tools.format

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object DateMapper {

    private const val MINUTES_IN_HOUR = 60
    private val dateFormatDayMonthYear = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun getMinutes(millis: Long): Long =
        TimeUnit.MILLISECONDS.toMinutes(millis) % MINUTES_IN_HOUR

    fun getSeconds(millis: Long): Long =
        TimeUnit.MILLISECONDS.toSeconds(millis) % MINUTES_IN_HOUR

    fun formatDayMonthYear(date: LocalDate): String =
        date.format(dateFormatDayMonthYear)

    fun toMillis(date: LocalDate): Long {
        val localDateTime = LocalDateTime.of(date, LocalTime.MIN)
        return toMillis(localDateTime)
    }

    fun toMillis(
        date: LocalDateTime,
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): Long {
        val zonedDateTime = ZonedDateTime.of(date, zoneId)
        val instant = zonedDateTime.toInstant()
        return instant.toEpochMilli()
    }
}