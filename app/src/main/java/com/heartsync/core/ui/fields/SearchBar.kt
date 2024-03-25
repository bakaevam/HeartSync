package com.heartsync.core.ui.fields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppTextField
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        SearchBar(
            query = "Search",
            onQueryChange = {},
            onClearClick = {},
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    AppTextField(
        modifier = modifier,
        value = query,
        singleLine = true,
        placeholder = stringResource(R.string.messages_search),
        leadingIcon = painterResource(R.drawable.ic_search),
        trailingIcon = if (query.isNotEmpty()) painterResource(R.drawable.ic_small_cross) else null,
        onValueChange = onQueryChange,
        onTrailingIconClick = onClearClick,
    )
}