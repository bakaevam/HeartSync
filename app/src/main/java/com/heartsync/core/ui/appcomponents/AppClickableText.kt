package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.skModernist
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        AppClickableText(
            text = AnnotatedString("Example text"),
            onClick = {},
        )
    }
}

@Composable
fun AppClickableText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = Int.MAX_VALUE,
    onClick: (Int) -> Unit,
) {
    ClickableText(
        modifier = modifier,
        text = text,
        style = style.copy(
            fontFamily = skModernist,
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onClick = onClick,
    )
}