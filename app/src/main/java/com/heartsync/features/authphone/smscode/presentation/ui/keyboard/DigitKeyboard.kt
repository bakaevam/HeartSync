package com.heartsync.features.authphone.smscode.presentation.ui.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heartsync.R

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DigitKeyboard(
        backspaceEnabled = true,
        onDigitClick = {},
        onBackspaceClick = {},
    )
}

private const val ZERO = 0

@Composable
fun DigitKeyboard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backspaceEnabled: Boolean,
    onDigitClick: (Char) -> Unit,
    onBackspaceClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (number in 1..3) {
                DigitKeyboardButton(
                    modifier = Modifier.weight(1f),
                    number = number,
                    enabled = enabled,
                    onClick = { onDigitClick(number.digitToChar()) },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (number in 4..6) {
                DigitKeyboardButton(
                    modifier = Modifier.weight(1f),
                    number = number,
                    enabled = enabled,
                    onClick = { onDigitClick(number.digitToChar()) },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (number in 7..9) {
                DigitKeyboardButton(
                    modifier = Modifier.weight(1f),
                    number = number,
                    enabled = enabled,
                    onClick = { onDigitClick(number.digitToChar()) },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Spacer(Modifier.weight(1f))
            DigitKeyboardButton(
                modifier = Modifier.weight(1f),
                number = ZERO,
                enabled = enabled,
                onClick = { onDigitClick(ZERO.digitToChar()) },
            )
            DigitKeyboardButton(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_backspace,
                enabled = backspaceEnabled,
                onClick = onBackspaceClick,
            )
        }
    }
}