package com.heartsync.features.chat.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppTextField
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        MessagesComposer(
            text = EMPTY_STRING,
            onTextChange = {},
            onSendClick = {},
        )
    }
}

@Composable
fun MessagesComposer(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    Row(
        modifier = modifier.heightIn(max = 100.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppTextField(
            modifier = Modifier.weight(1f),
            value = text,
            placeholder = stringResource(R.string.chat_placeholder),
            onValueChange = onTextChange,
        )
        AppButton(
            type = Type.OUTLINE,
            icon = painterResource(R.drawable.ic_send),
            onClick = onSendClick,
        )
    }
}