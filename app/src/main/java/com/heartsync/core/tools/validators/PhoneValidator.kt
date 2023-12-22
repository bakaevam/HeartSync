package com.heartsync.core.tools.validators

object PhoneValidator {

    private const val MAX_PHONE_LENGTH = 10

    fun validate(phone: String): String =
        phone
            .filter(Char::isDigit)
            .take(MAX_PHONE_LENGTH)
}