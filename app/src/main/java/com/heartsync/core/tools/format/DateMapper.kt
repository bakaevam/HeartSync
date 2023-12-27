package com.heartsync.core.tools.format

import java.util.concurrent.TimeUnit

object DateMapper {

    private const val MINUTES_IN_HOUR = 60

    fun getMinutes(millis: Long): Long =
        TimeUnit.MILLISECONDS.toMinutes(millis) % MINUTES_IN_HOUR

    fun getSeconds(millis: Long): Long =
        TimeUnit.MILLISECONDS.toSeconds(millis) % MINUTES_IN_HOUR
}