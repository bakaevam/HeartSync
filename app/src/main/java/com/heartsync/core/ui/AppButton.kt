package com.heartsync.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

enum class Type {
    REGULAR,
    OUTLINE,
    GHOST,
}

@AppPreview
@Composable
private fun PreviewRegular() {
    HeartSyncTheme {
        AppButton(
            text = "Button",
            onClick = {},
        )
    }
}

@AppPreview
@Composable
private fun PreviewGhost() {
    HeartSyncTheme {
        AppButton(
            text = "Button",
            type = Type.GHOST,
            onClick = {},
        )
    }
}

@AppPreview
@Composable
private fun PreviewOutline() {
    HeartSyncTheme {
        AppButton(
            text = "Button",
            type = Type.OUTLINE,
            onClick = {},
        )
    }
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Painter? = null,
    type: Type = Type.REGULAR,
    onClick: () -> Unit,
) {
    val background = when(type) {
        Type.REGULAR -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }
    val border = when(type) {
        Type.OUTLINE -> BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        )
        else -> null
    }
    Button(
        modifier = modifier
            .padding(0.dp)
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
        ),
        border = border,
        onClick = onClick,
    ) {
        Row {
            icon?.let {
                val tint = when (type) {
                    Type.REGULAR -> MaterialTheme.colorScheme.onPrimary
                    Type.GHOST -> Color.Transparent
                    else -> MaterialTheme.colorScheme.primary
                }
                AppIcon(
                    painter = icon,
                    tint = tint,
                )
            }
            text?.let {
                val textColor = when(type) {
                    Type.OUTLINE, Type.GHOST -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onPrimary
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = textColor,
                    ),
                )
            }
        }
    }
}