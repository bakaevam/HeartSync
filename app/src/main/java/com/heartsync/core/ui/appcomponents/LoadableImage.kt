package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        LoadableImage(imageUrl = "")
    }
}

@Composable
fun LoadableImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    AsyncImage(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(false)
            .build(),
        placeholder = ColorPainter(MaterialTheme.colorScheme.surface),
        // error = painterResource(id = R.drawable.ic_placeholder),
        contentDescription = null,
        contentScale = contentScale,
        alignment = Alignment.Center,
    )
}