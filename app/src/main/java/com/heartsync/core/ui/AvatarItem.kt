package com.heartsync.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.appcomponents.LoadableImage
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.LargeCornerShape
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        AvatarItem(
            avatarUrl = EMPTY_STRING,
        )
    }
}

@Composable
fun AvatarItem(
    avatarUrl: String?,
    modifier: Modifier = Modifier,
    onEditClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
        LoadableImage(
            modifier = Modifier
                .size(99.dp)
                .clip(LargeCornerShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = LargeCornerShape,
                ),
            imageUrl = avatarUrl,
            contentScale = ContentScale.Crop,
        )
        if (onEditClick != null) {
            AppIcon(
                modifier = Modifier
                    .size(34.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (2).dp, y = (7).dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape,
                    )
                    .clickable(
                        onClick = onEditClick,
                    )
                    .padding(9.dp),
                painter = painterResource(R.drawable.ic_camera),
            )
        }
    }
}