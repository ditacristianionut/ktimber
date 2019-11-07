package com.dci.dev.ktimber

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

// Extension function to get host application name
internal fun Context.appName(): String {
    val packageManager = packageManager
    var applicationInfo: ApplicationInfo? = null
    try {
        applicationInfo =
            packageManager.getApplicationInfo(getApplicationInfo().packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e)
    }
    return if (applicationInfo != null)
        packageManager.getApplicationLabel(applicationInfo) .toString()
    else
        "UnknownAppName"
}

// Extension function to share logs file
fun AppCompatActivity.shareLogsFile(emailAddress: String) {
    shareLogsFile(this, emailAddress)
}

// Extension function to share logs file
fun AppCompatActivity.openLogsFile() {
    openLogsFile(this)
}

// Extension function to delete logs file
fun AppCompatActivity.deleteLogsFile() {
    deleteLogsFile(this)
}

