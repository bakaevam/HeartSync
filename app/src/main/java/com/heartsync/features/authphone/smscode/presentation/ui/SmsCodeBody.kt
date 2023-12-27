package com.heartsync.features.authphone.smscode.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.authphone.smscode.presentation.ui.keyboard.DigitKeyboard
import com.heartsync.features.authphone.smscode.presentation.viewmodels.SmsCodeAction
import com.heartsync.features.authphone.smscode.presentation.viewmodels.SmsCodeState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        SmsCodeBody(
            state = SmsCodeState(
                code = listOf('1'),
                timer = "00:42",
                maxLength = 4,
            ),
            onAction = {},
        )
    }
}

@Composable
fun SmsCodeBody(
    state: SmsCodeState,
    modifier: Modifier = Modifier,
    onAction: (SmsCodeAction) -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.timer?.let { timer ->
                AppText(
                    text = timer,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
            Spacer(Modifier.height(4.dp))
            AppText(
                text = stringResource(R.string.sms_code_description),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(40.dp))
            SmsCodeField(
                code = state.code,
                maxLength = state.maxLength,
                inputLength = state.code.size,
            )
        }
        Spacer(Modifier.height(40.dp))
        DigitKeyboard(
            modifier = Modifier.padding(horizontal = 6.dp),
            backspaceEnabled = state.backspaceEnabled,
            onDigitClick = { digit -> onAction(SmsCodeAction.OnKeyClick(digit)) },
            onBackspaceClick = { onAction(SmsCodeAction.OnBackspaceClick) },
        )
        Spacer(Modifier.height(40.dp))
        AppButton(
            text = stringResource(R.string.sms_code_resend_code),
            type = Type.GHOST,
            enabled = state.resendCodeEnabled,
            onClick = { onAction(SmsCodeAction.OnResendCodeClick) },
        )
        Spacer(Modifier.weight(1f))
    }
}