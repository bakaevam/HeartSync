package com.heartsync.features.main.data.providers

import android.content.Context
import android.content.res.Resources

class ContextProvider(
    private val context: Context,
) {

    fun getResources(): Resources =
        context.resources
}