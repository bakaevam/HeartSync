package com.heartsync.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        InfoBody(
            modifier = Modifier.fillMaxSize(),
            title = "Заголовок",
            message = "Текст сообщения ".repeat(10),
            button = "Кнопка",
            onClick = {},
            onBackClick = {},
        )
    }
}

@Composable
fun InfoBody(
    title: String,
    message: String,
    button: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 24.dp),
    ) {
        AppButton(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(R.string.navigation_back),
            type = Type.OUTLINE,
            onClick = onBackClick,
        )
        Spacer(Modifier.height(40.dp))
        AppText(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(Modifier.height(24.dp))
        AppText(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(24.dp))
        Spacer(Modifier.weight(1f))
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = button,
            onClick = onClick,
        )
    }
}