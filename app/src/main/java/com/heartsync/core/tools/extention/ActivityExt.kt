package com.heartsync.core.tools.extention

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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

fun Activity.openAppSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.parse("package:$packageName")
    )
    startActivity(intent)
}