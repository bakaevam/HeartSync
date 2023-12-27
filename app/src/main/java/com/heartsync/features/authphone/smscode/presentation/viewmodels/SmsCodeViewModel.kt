package com.heartsync.features.authphone.smscode.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.LONG_ZERO
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository

class SmsCodeViewModel(
    private val smsCodeRepository: SmsCodeRepository,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<SmsCodeState, SmsCodeEffect, SmsCodeAction>(
    SmsCodeState(
        maxLength = SMS_CODE_LENGTH,
    )
) {

    private val smsCodeList = mutableListOf<Char>()
    private var timer: CountDownTimer? = null

    init {
        getSavedArguments()
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        timer = null
    }

    override fun onAction(action: SmsCodeAction) = when (action) {
        is SmsCodeAction.OnBackspaceClick -> onBackspaceClick()
        is SmsCodeAction.OnKeyClick -> onKeyClick(action)
        is SmsCodeAction.OnResendCodeClick -> onResendCodeClick()
    }

    private fun onKeyClick(action: SmsCodeAction.OnKeyClick) {
        val key = action.digit
        if (smsCodeList.size < SMS_CODE_LENGTH) {
            smsCodeList.add(key)
            setState { copy(code = smsCodeList.toList()) }
        }
        validate()
    }

    private fun onBackspaceClick() {
        if (smsCodeList.isNotEmpty()) {
            smsCodeList.removeLast()
            setState { copy(code = smsCodeList.toList()) }
        }
        validate()
    }

    private fun onResendCodeClick() {
        startTimer()
        setState { copy(resendCodeEnabled = false) }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(TIMER_MILLIS, ONE_SECOND_MILLIS) {

            override fun onTick(time: Long) {
                updateTimer(time)
            }

            override fun onFinish() {
                timer = null
                setState { copy(resendCodeEnabled = true) }
            }
        }.start()
    }

    private fun updateTimer(time: Long = LONG_ZERO) {
        setState { copy(timer = smsCodeRepository.getTimer(time)) }
    }

    private fun validate() {
        setState {
            copy(
                backspaceEnabled = smsCodeList.isNotEmpty(),
            )
        }
    }

    private fun getSavedArguments() {
        setState {
            copy(
                phone = savedStateHandle.get<String>(Destination.SmsCodeScreen.KEY_PHONE)
                    ?: EMPTY_STRING,
            )
        }
    }

    private companion object {
        private const val SMS_CODE_LENGTH = 4
        private const val TIMER_MILLIS = 60000L
        private const val ONE_SECOND_MILLIS = 1000L
    }
}