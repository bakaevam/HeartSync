package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.extention.condition
import com.heartsync.core.ui.model.UiChipsState
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        Column {
            AppChips(
                title = "Man",
                state = UiChipsState.SELECTED,
                onClick = {},
            )
            AppChips(
                title = "Man",
                state = UiChipsState.NOT_SELECTED,
                onClick = {},
            )
        }
    }
}

@Composable
fun AppChips(
    title: String,
    state: UiChipsState,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    onClick: () -> Unit,
) {
    val containerColor = when (state) {
        UiChipsState.SELECTED -> MaterialTheme.colorScheme.primary
        UiChipsState.NOT_SELECTED -> MaterialTheme.colorScheme.background
    }
    val contentColor = when (state) {
        UiChipsState.SELECTED -> MaterialTheme.colorScheme.background
        UiChipsState.NOT_SELECTED -> MaterialTheme.colorScheme.primary
    }
    Row(
        modifier = modifier
            .clip(MediumCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick,
            )
            .shadow(elevation = 8.dp)
            .condition(
                condition = state == UiChipsState.NOT_SELECTED,
                then = {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MediumCornerShape,
                    )
                }
            )
            .background(color = containerColor, shape = MediumCornerShape)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (icon != null) {
            AppIcon(
                modifier = Modifier.size(19.dp),
                painter = icon,
                tint = contentColor,
            )
        }
        val textColor = when (state) {
            UiChipsState.SELECTED -> MaterialTheme.colorScheme.background
            UiChipsState.NOT_SELECTED -> MaterialTheme.colorScheme.onBackground
        }
        AppText(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor,
            )
        )
    }
}