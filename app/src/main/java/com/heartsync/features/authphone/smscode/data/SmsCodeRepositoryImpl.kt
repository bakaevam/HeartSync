package com.heartsync.features.authphone.smscode.data

import com.heartsync.R
import com.heartsync.core.providers.TextProvider
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository

class SmsCodeRepositoryImpl(
    private val textProvider: TextProvider,
) : SmsCodeRepository {

    override fun getTimer(millisLeft: Long): String =
        textProvider.getString(
            R.string.sms_code_timer,
            DateMapper.getMinutes(millisLeft),
            DateMapper.getSeconds(millisLeft),
        )
}