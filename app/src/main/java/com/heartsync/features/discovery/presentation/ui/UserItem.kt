package com.heartsync.features.discovery.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.LoadableImage
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.discovery.presentation.models.UiDiscoveryUser

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        UserItem(
            user = UiDiscoveryUser(
                imageUrl = "https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png?w=1200",
                nameAge = "Duncan Vance, 23",
                profession = "semper",
                id = EMPTY_STRING,
                uid = EMPTY_STRING,
            ),
            onMessageClick = {},
        )
    }
}

@Composable
fun UserItem(
    user: UiDiscoveryUser,
    modifier: Modifier = Modifier,
    onMessageClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(MediumCornerShape),
    ) {
        LoadableImage(
            modifier = Modifier
                .height(400.dp)
                .align(Alignment.Center),
            imageUrl = user.imageUrl,
            contentScale = ContentScale.FillHeight,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .blur(radius = 70.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .background(color = Color.Black)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 6.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(
                    text = user.nameAge,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                    ),
                )
                AppButton(
                    type = Type.REGULAR,
                    icon = painterResource(R.drawable.ic_send),
                    onClick = onMessageClick,
                )
            }
        }
    }
}