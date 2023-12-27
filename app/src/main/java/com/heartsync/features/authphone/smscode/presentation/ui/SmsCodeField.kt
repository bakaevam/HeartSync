package com.heartsync.features.authphone.smscode.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.INT_ZERO
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.authphone.smscode.presentation.models.UiSmsCodeState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        SmsCodeField(
            code = listOf('1', '4'),
            maxLength = 4,
            inputLength = 2,
        )
    }
}

@Composable
fun SmsCodeField(
    code: List<Char>,
    maxLength: Int,
    inputLength: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            for (charIndex in INT_ZERO until maxLength) {
                val char = code.getOrNull(charIndex)
                val state = when {
                    charIndex == inputLength -> UiSmsCodeState.CURRENT
                    char == null -> UiSmsCodeState.EMPTY
                    else -> UiSmsCodeState.FILLED
                }
                SmsCodeFieldItem(
                    modifier = Modifier.weight(1f),
                    char = char,
                    state = state,
                )
            }
        }
    }
}

@Composable
private fun SmsCodeFieldItem(
    char: Char?,
    state: UiSmsCodeState,
    modifier: Modifier = Modifier,
) {
    val background = when (state) {
        UiSmsCodeState.FILLED -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.background
    }
    val border = when (state) {
        UiSmsCodeState.EMPTY -> Modifier.border(
            shape = MediumCornerShape,
            color = MaterialTheme.colorScheme.outline,
            width = 1.dp,
        )

        UiSmsCodeState.CURRENT -> Modifier.border(
            shape = MediumCornerShape,
            color = MaterialTheme.colorScheme.primary,
            width = 1.dp,
        )

        UiSmsCodeState.FILLED -> Modifier
    }
    Box(
        modifier = modifier
            .size(width = 67.dp, height = 70.dp)
            .background(color = background, shape = MediumCornerShape)
            .then(border),
    ) {
        char?.let { digit ->
            AppText(
                modifier = Modifier.align(Alignment.Center),
                text = digit.toString(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}