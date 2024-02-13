package com.heartsync.features.welcome.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.LoadableImage
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.welcome.presentation.models.UiWelcomePage
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        Gallery(
            modifier = Modifier,
            pages = listOf(
                UiWelcomePage(
                    photoUrl = "https://img.freepik.com/free-photo/vertical-image-gorgeous-fashionable-young-female-model-with-slim-body-posing-orange-studio_343059-3404.jpg?w=360&t=st=1702710263~exp=1702710863~hmac=7c86654d59c6fd3a1ec20e42c1cd8519ff444fead3a593feba1d42857a3a5e73",
                    title = "Title 1",
                    description = "Users going through a vetting process to ensure you never match with bots.",
                ),
                UiWelcomePage(
                    photoUrl = "https://a.d-cd.net/b38ff71s-960.jpg",
                    title = "Title 2",
                    description = "We match you with people that have a large array of similar interests.",
                ),
                UiWelcomePage(
                    photoUrl = "https://i.pinimg.com/736x/a1/07/4e/a1074ea33e2c1adfecb4b86487d7ea9d--male-fitness-photography-bodybuilding-photography.jpg",
                    title = "Title 3",
                    description = "Sign up today and enjoy the first month of premium benefits on us.",
                ),
            ),
        )
    }
}

private const val PAGER_SCROLL_DELAY = 2000L

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery(
    pages: List<UiWelcomePage>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val pagerState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = Int.MAX_VALUE / 2,
    )
    LaunchedEffect(Unit) {
        while (pagerState.canScrollForward) {
            delay(PAGER_SCROLL_DELAY)
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + INT_ONE,
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            HorizontalPager(
                modifier = modifier.fillMaxWidth(),
                state = pagerState,
                pageSpacing = 24.dp,
                contentPadding = PaddingValues(horizontal = 70.dp)
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val scaleFactor = 0.85f + (1f - 0.85f) * (1f - pageOffset.absoluteValue)
                val welcomePage = pages[page % pages.size]
                LoadableImage(
                    modifier = modifier
                        .height(360.dp)
                        .graphicsLayer {
                            scaleY = scaleFactor
                            scaleX = scaleFactor
                        }
                        .clip(MediumCornerShape),
                    imageUrl = welcomePage.photoUrl,
                    contentScale = ContentScale.Crop,
                )
            }
        }
        val welcomePage = pages[pagerState.currentPage % pages.size]
        Spacer(Modifier.height(24.dp))
        AppText(
            modifier = Modifier.padding(contentPadding),
            text = welcomePage.title,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
        Spacer(Modifier.height(10.dp))
        AppText(
            modifier = Modifier.padding(contentPadding),
            text = welcomePage.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
        Spacer(Modifier.height(12.dp))
        HorizontalPagerIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = 3,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.outline,
            indicatorHeight = 8.dp,
            indicatorWidth = 8.dp,
            pageIndexMapping = { pagerState.currentPage % 3 }
        )
    }
}