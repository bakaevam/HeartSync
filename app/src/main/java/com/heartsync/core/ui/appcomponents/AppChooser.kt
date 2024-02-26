package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.model.UiChipsState
import com.heartsync.core.ui.model.UiChooserItem
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        AppChooser(
            items = setOf(
                UiChooserItem(
                    id = "quem",
                    title = "elit"
                ),
                UiChooserItem(
                    id = "detraxit",
                    title = "facilisis"
                )
            ),
            selectId = "detraxit",
            onClick = {},
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppChooser(
    items: Set<UiChooserItem>,
    selectId: String?,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.forEach { item ->
            AppChips(
                title = item.title,
                state = if (selectId == item.id) {
                    UiChipsState.SELECTED
                } else {
                    UiChipsState.NOT_SELECTED
                },
                onClick = { onClick(item.id) }
            )
        }
    }
}