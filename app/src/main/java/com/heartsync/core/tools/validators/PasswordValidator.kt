package com.heartsync.core.tools.validators

object PasswordValidator {

    private const val MIN_PASSWORD_LENGTH = 6

    fun newPasswordValidate(
        password: String,
        repeatPassword: String,
    ): Boolean =
        password.length >= MIN_PASSWORD_LENGTH
            && password == repeatPassword
}