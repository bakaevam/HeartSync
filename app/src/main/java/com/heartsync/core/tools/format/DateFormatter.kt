package com.heartsync.core.tools.format

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val jsonDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun formatDateString(date: LocalDate): String =
        date.format(jsonDateFormatter)
}