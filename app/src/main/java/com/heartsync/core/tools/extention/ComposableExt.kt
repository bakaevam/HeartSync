package com.heartsync.core.tools.extention

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.INT_ZERO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
inline fun <T> CollectEffect(
    source: Flow<T>?,
    crossinline consumer: suspend (T) -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
            source?.collectLatest { effect ->
                consumer(effect)
            }
        }
    }
}

@Composable
fun OnLifecycleEvent(
    onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit,
) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun LazyListState.OnListEndReached(
    buffer: Int = INT_ZERO,
    handler: () -> Unit,
) {
    val shouldCallHandler by remember {
        derivedStateOf {
            layoutInfo.visibleItemsInfo.lastOrNull()?.let { lastVisibleItem ->
                lastVisibleItem.index == layoutInfo.totalItemsCount - INT_ONE - buffer
            } ?: false
        }
    }

    LaunchedEffect(shouldCallHandler) {
        if (shouldCallHandler) handler()
    }
}

@SuppressLint("UnnecessaryComposedModifier")
inline fun Modifier.condition(
    condition: Boolean,
    crossinline then: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) {
        then()
    } else {
        this
    }
}

@SuppressLint("UnnecessaryComposedModifier")
inline fun Modifier.condition(
    condition: Boolean,
    crossinline then: @Composable Modifier.() -> Modifier,
    crossinline another: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) {
        then()
    } else {
        another(this)
    }
}