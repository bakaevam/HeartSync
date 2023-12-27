package com.heartsync.features.authphone.smscode.domain

interface SmsCodeRepository {

    fun getTimer(millisLeft: Long): String
}