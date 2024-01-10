package com.heartsync.features.discovery.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type

@Composable
fun DiscoveryHeader(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AppText(
            modifier = Modifier.padding(end = 20.dp),
            text = stringResource(R.string.discovery_title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        AppButton(
            icon = painterResource(R.drawable.ic_filter),
            type = Type.OUTLINE,
            onClick = onFilterClick,
        )
    }
}