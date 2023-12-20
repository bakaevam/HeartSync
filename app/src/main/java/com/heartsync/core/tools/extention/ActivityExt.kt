package com.heartsync.core.tools.extention

import android.app.Activity
import android.widget.Toast
import com.heartsync.R

fun Activity.showToast(
    message: CharSequence?,
    duration: Int = Toast.LENGTH_LONG,
) = Toast
    .makeText(
        baseContext,
        message ?: getString(R.string.error_default),
        duration
    )
    .show()