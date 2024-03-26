package com.heartsync.features.chat.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.chat.ChatAvatar
import com.heartsync.core.ui.chat.OnlineStatus
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        ChatHeader(
            name = "Nick",
            image = EMPTY_STRING,
            online = true,
            onBackClick = {},
        )
    }
}

@Composable
fun ChatHeader(
    name: String,
    image: String,
    online: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppButton(
            icon = painterResource(R.drawable.ic_back),
            type = Type.OUTLINE,
            onClick = onBackClick,
        )
        Spacer(Modifier.width(10.dp))
        ChatAvatar(
            image = image,
            name = name,
        )
        Column {
            AppText(
                text = name,
                maxLines = INT_ONE,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            OnlineStatus(online = online)
        }
    }
}