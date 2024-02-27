package com.heartsync.features.messages.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.skModernist
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamTypography
import io.getstream.chat.android.models.InitializationState

@Composable
fun MessagesBody(
    state: InitializationState?,
    modifier: Modifier = Modifier,
) {
    ChatTheme(
        typography = StreamTypography.defaultTypography(skModernist),
    ) {
        when (state) {
            InitializationState.COMPLETE -> {
                ChannelsScreen(
                    title = stringResource(R.string.messages_title),
                    isShowingSearch = true,
                    onItemClick = { channel ->

                    },
                )
            }

            InitializationState.INITIALIZING -> {
                AppText(text = "Initialising...")
            }

            else -> {
                AppText(text = "Not initialized...")
            }
        }
    }
}