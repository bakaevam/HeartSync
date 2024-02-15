package com.heartsync.features.main.data.providers

import android.content.Context
import android.content.res.Resources
import androidx.core.app.ActivityCompat

class ContextProvider(
    private val context: Context,
) {

    fun getResources(): Resources =
        context.resources

    fun checkPermission(permission: String): Int =
        ActivityCompat.checkSelfPermission(context, permission)
}