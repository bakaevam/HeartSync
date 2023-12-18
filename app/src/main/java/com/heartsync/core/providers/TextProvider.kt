package com.heartsync.core.providers

import android.content.res.Resources

class TextProvider(
    private val resources: Resources,
) {

    fun getString(id: Int): String =
        resources.getString(id)

    fun getStringArray(id: Int): Array<String> =
        resources.getStringArray(id)
}