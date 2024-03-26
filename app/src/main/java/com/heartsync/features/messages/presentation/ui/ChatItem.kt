package com.heartsync.features.messages.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.INT_ZERO
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.chat.ChatAvatar
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.messages.presentation.model.UiChatItem

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        ChatItem(
            chat = UiChatItem(
                image = "elit",
                name = "Virgie Kerr",
                lastMessage = "scripserit",
                time = "23 min",
                unreadMessages = 1,
                cid = EMPTY_STRING,
            ),
            onClick = {},
        )
    }
}

@Composable
fun ChatItem(
    chat: UiChatItem,
    modifier: Modifier = Modifier,
    divider: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChatAvatar(
            image = chat.image,
            name = chat.name,
        )
        Column {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    AppText(
                        text = chat.name,
                        maxLines = INT_ONE,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    AppText(
                        text = chat.lastMessage,
                        maxLines = INT_ONE,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    AppText(
                        text = chat.time,
                        maxLines = INT_ONE,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.outline,
                        ),
                    )
                    if (chat.unreadMessages != null && chat.unreadMessages > INT_ZERO) {
                        AppText(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ),
                            text = chat.unreadMessages.toString(),
                            maxLines = INT_ONE,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.background,
                            ),
                        )
                    }
                }
            }
            if (divider) {
                Divider(Modifier.padding(top = 6.dp))
            }
        }
    }
}
