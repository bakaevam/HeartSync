package com.heartsync.core.tools.extention

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

fun <R> Flow<R>.toStateFlow(
    coroutineScope: CoroutineScope,
    initialValue: R
) = stateIn(coroutineScope, SharingStarted.Lazily, initialValue)