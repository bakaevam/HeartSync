package com.heartsync.features.authphone.smscode.domain

import com.google.firebase.auth.AuthResult

interface SmsCodeRepository {

    fun getTimer(millisLeft: Long): String

    suspend fun checkSmsCode(code: String): AuthResult
}