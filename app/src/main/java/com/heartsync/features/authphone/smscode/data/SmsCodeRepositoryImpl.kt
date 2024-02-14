package com.heartsync.features.authphone.smscode.data

import com.google.firebase.auth.AuthResult
import com.heartsync.R
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider

class SmsCodeRepositoryImpl(
    private val textProvider: TextProvider,
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : SmsCodeRepository {

    override fun getTimer(millisLeft: Long): String =
        textProvider.getString(
            R.string.sms_code_timer,
            DateMapper.getMinutes(millisLeft),
            DateMapper.getSeconds(millisLeft),
        )

    override suspend fun checkSmsCode(code: String): AuthResult =
        firebaseAuthProvider.signUpByPhone(code)

}