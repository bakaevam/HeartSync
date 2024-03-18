package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
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
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val background = when {
        enabled && type == Type.REGULAR -> MaterialTheme.colorScheme.primary
        type == Type.REGULAR -> MaterialTheme.colorScheme.outline
        else -> Color.Transparent
    }
    val border = when (type) {
        Type.OUTLINE -> BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        )

        else -> null
    }
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            disabledContainerColor = background,
        ),
        border = border,
        contentPadding = PaddingValues(16.dp),
        shape = MediumCornerShape,
        enabled = enabled,
        onClick = onClick,
    ) {
        Row {
            icon?.let {
                val tint = when (type) {
                    Type.REGULAR -> MaterialTheme.colorScheme.onPrimary
                    Type.GHOST -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.primary
                }
                AppIcon(
                    painter = icon,
                    tint = tint,
                )
            }
            text?.let {
                val textColor = when (type) {
                    Type.OUTLINE, Type.GHOST -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onPrimary
                }
                val disableContentColor = when (type) {
                    Type.OUTLINE, Type.GHOST -> MaterialTheme.colorScheme.outline
                    else -> MaterialTheme.colorScheme.onPrimary
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = if (enabled) {
                            textColor
                        } else {
                            disableContentColor
                        },
                    ),
                )
            }
        }
    }
}