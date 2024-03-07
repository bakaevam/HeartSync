package com.heartsync.features.messages.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.appcomponents.AppText
import io.getstream.chat.android.models.Channel

@Composable
fun ChatItem(
    channel: Channel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        // 1
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar(channel) // 2
        val context = LocalContext.current
        AppText(
            text = channel.name,
        )

        val lastMessageText = channel.messages.lastOrNull()?.text ?: "..."
        AppText(
            text = lastMessageText,
            maxLines = 1,
        )
    }
}
