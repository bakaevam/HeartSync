package com.heartsync.core.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.INT_ZERO
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.LoadableImage

@Composable
fun ChatAvatar(
    image: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    if (image.isNotEmpty()) {
        LoadableImage(
            modifier = modifier
                .padding(end = 10.dp)
                .clip(CircleShape)
                .size(48.dp),
            imageUrl = image,
        )
    } else {
        Box(
            modifier = modifier
                .padding(end = 10.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (name.isNotEmpty()) {
                AppText(
                    text = name[INT_ZERO].toString(),
                    maxLines = INT_ONE,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.background,
                    ),
                )
            }
        }
    }
}