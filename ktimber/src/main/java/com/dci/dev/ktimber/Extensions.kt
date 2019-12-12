package com.dci.dev.ktimber

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.annotation.Keep
import timber.log.Timber

// Extension function to get host application name
@Keep
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
@Keep
fun Activity.shareLogsFile(emailAddress: String) {
    shareLogsFile(this, emailAddress)
}

// Extension function to share logs file
@Keep
fun Activity.openLogsFile() {
    openLogsFile(this)
}

// Extension function to delete logs file
@Keep
fun Activity.deleteLogsFile() {
    deleteLogsFile(this)
}

