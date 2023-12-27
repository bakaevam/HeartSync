package com.heartsync.features.main.presentation.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.theme.AdditionalColor
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.main.presentation.models.UiBottomItem
import com.heartsync.features.main.presentation.models.UiNavItem

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        BottomNavBar(
            items = listOf(
                UiNavItem(
                    bottomItem = UiBottomItem.DISCOVERY,
                    selected = true,
                    badgeText = null,
                ),
                UiNavItem(
                    bottomItem = UiBottomItem.MATCHERS,
                    selected = true,
                    badgeText = null,
                ),
                UiNavItem(
                    bottomItem = UiBottomItem.MESSAGES,
                    selected = true,
                    badgeText = null,
                ),
                UiNavItem(
                    bottomItem = UiBottomItem.MENU,
                    selected = true,
                    badgeText = null,
                ),
            ),
            currentNavItem = UiBottomItem.DISCOVERY,
            onItemClick = {},
        )
    }
}

@Composable
fun BottomNavBar(
    items: List<UiNavItem>,
    currentNavItem: UiBottomItem?,
    modifier: Modifier = Modifier,
    onItemClick: (UiBottomItem) -> Unit,
) {
    BottomNavigation(
        modifier = modifier,
        elevation = 2.dp,
        contentColor = MaterialTheme.colorScheme.outline,
        backgroundColor = AdditionalColor.Background,
    ) {
        items.forEach { item ->
            val icon = when (item.bottomItem) {
                UiBottomItem.DISCOVERY -> R.drawable.ic_discovery
                UiBottomItem.MATCHERS -> R.drawable.ic_matchers
                UiBottomItem.MESSAGES -> R.drawable.ic_chats
                UiBottomItem.MENU -> R.drawable.ic_menu
            }
            val selected = item.bottomItem == currentNavItem
            BottomNavigationItem(
                selected = selected,
                icon = {
                    val tint = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    }
                    AppIcon(
                        painter = painterResource(icon),
                        tint = tint,
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = { onItemClick(item.bottomItem) },
            )
        }
    }
}