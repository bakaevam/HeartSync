package com.heartsync.core.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.AdditionalColor
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        OnlineStatus(online = true)
    }
}

@Composable
fun OnlineStatus(
    online: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val color = when {
            online -> AdditionalColor.Success
            else -> MaterialTheme.colorScheme.error
        }
        Spacer(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color = color, shape = CircleShape),
        )
        AppText(
            text = stringResource(R.string.chat_online),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline,
            ),
        )
    }
}