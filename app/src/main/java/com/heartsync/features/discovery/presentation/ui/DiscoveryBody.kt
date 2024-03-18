package com.heartsync.features.discovery.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.discovery.presentation.models.UiDiscoveryUser
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryAction
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        DiscoveryBody(
            state = DiscoveryState(
                people = listOf(
                    UiDiscoveryUser(
                        id = EMPTY_STRING,
                        imageUrl = "https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png?w=1200",
                        nameAge = "Duncan Vance, 23",
                        profession = "semper",
                        uid = EMPTY_STRING,
                    ),
                    UiDiscoveryUser(
                        id = EMPTY_STRING,
                        imageUrl = "https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png?w=1200",
                        nameAge = "Duncan Vance, 23",
                        profession = "semper",
                        uid = EMPTY_STRING,
                    ),
                    UiDiscoveryUser(
                        id = EMPTY_STRING,
                        imageUrl = "https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png?w=1200",
                        nameAge = "Duncan Vance, 23",
                        profession = "semper",
                        uid = EMPTY_STRING,
                    ),
                    UiDiscoveryUser(
                        id = EMPTY_STRING,
                        imageUrl = "https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png?w=1200",
                        nameAge = "Duncan Vance, 23",
                        profession = "semper",
                        uid = EMPTY_STRING,
                    ),
                )
            ),
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
    ) {
        DiscoveryHeader(
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 24.dp),
            onFilterClick = { onAction(DiscoveryAction.OnFilterClick) },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.people) { user ->
                UserItem(
                    user = user,
                    onMessageClick = { onAction(DiscoveryAction.OnMessageClick(user.uid)) },
                )
            }
        }
    }
}