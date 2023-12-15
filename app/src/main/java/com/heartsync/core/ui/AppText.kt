package com.heartsync.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.skModernist
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        AppText(text = "Example text")
    }
}

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textDecoration: TextDecoration? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(
            fontFamily = skModernist,
        ),
        textDecoration = textDecoration,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
    )
}