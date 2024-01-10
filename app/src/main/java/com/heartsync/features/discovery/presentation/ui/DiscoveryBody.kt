package com.heartsync.features.discovery.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryAction
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        DiscoveryBody(
            state = DiscoveryState(),
            onAction = {},
        )
    }
}

@Composable
fun DiscoveryBody(
    state: DiscoveryState,
    modifier: Modifier = Modifier,
    onAction: (DiscoveryAction) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp, vertical = 24.dp)
    ) {
        DiscoveryHeader(
            onFilterClick = { onAction(DiscoveryAction.OnFilterClick) },
        )

    }
}