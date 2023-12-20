package com.heartsync.features.signup.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        SignUpSocialTitle()
    }
}

@Composable
fun SignUpSocialTitle(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            Modifier
                .height(0.5.dp)
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.outline),
        )
        AppText(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = stringResource(R.string.sign_up_social),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(
            Modifier
                .height(0.5.dp)
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.outline),
        )
    }
}