package com.heartsync.core.tools.format

object PhoneFormatter {

    private const val E_164_FORMAT_RUS_PREFIX = "+7"

    fun e164formatPhone(phone: String): String =
        E_164_FORMAT_RUS_PREFIX + phone
}