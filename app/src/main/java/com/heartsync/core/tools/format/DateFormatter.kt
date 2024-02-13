package com.heartsync.core.tools.format

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val jsonDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun formatDateString(date: LocalDate): String =
        date.format(jsonDateFormatter)

    fun toLocalDate(date: String): LocalDate? =
        try {
            LocalDate.parse(date, jsonDateFormatter)
        } catch (e: Throwable) {
            Log.e("DateTime", "Failed to parse dateTime $this", e)
            null
        }
}