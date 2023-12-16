package com.heartsync.core.ui.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun BlockingLoader(
    modifier: Modifier = Modifier,
) {
    Box(modifier.pointerInput(Unit) {}) {
        ProgressBar(Modifier.align(Alignment.Center))
    }
}