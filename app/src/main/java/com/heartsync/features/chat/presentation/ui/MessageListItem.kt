package com.heartsync.features.chat.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.extention.condition
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.AdditionalColor
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.chat.presentation.model.UiMessage

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        MessageListItem(
            message = UiMessage(
                isMine = true,
                text = "Hi Jake, how are you? I saw on the app that we’ve crossed paths several times this week \uD83D\uDE04",
                time = "2:55",
                read = false
            ),
        )
    }
}

@Composable
fun MessageListItem(
    message: UiMessage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val shape = when {
            message.isMine -> RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp,
                bottomStart = 15.dp
            )

            else -> RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp)
        }
        val color = when {
            message.isMine -> AdditionalColor.Background
            else -> AdditionalColor.MessageBackground
        }
        AppText(
            modifier = Modifier
                .clip(shape)
                .background(color = color)
                .padding(16.dp)
                .condition(
                    condition = message.isMine,
                    then = { align(Alignment.End) },
                ),
            text = message.text,
            style = MaterialTheme.typography.bodyMedium,
        )
        AppText(
            modifier = Modifier.condition(
                condition = message.isMine,
                then = { align(Alignment.End) },
            ),
            text = message.time,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
        )
    }
}