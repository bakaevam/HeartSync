package com.heartsync.core.tools.format

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object DateMapper {

    private const val MINUTES_IN_HOUR = 60
    private const val HOURS_IN_DAY = 24
    private val dateFormatDayMonthYear = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun getDays(millis: Long): Long =
        TimeUnit.DAYS.toDays(millis)

    fun getHours(millis: Long): Long =
        TimeUnit.HOURS.toHours(millis) % HOURS_IN_DAY

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

    fun millisToLocalDateTime(millis: Long): LocalDateTime {
        val zonedDateTime = millisToZonedDateTime(millis)
        return zonedDateTime.toLocalDateTime()
    }

    private fun millisToZonedDateTime(
        millis: Long,
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): ZonedDateTime {
        val instant = Instant.ofEpochMilli(millis)
        return ZonedDateTime.ofInstant(instant, zoneId)
    }
}