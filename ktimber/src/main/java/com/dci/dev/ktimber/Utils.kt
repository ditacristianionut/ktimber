package com.dci.dev.ktimber

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import timber.log.Timber
import java.io.File


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

internal fun fileName(context: Context) = context.appName() + ".html"

/*  Helper method to create file*/
internal fun generateFile(context: Context): File? {
    var file: File? = null
    if (isExternalStorageAvailable()) {
        val root = File(context.getExternalFilesDir(null)!!.toURI())
        var dirExists = true
        if (!root.exists()) {
            dirExists = root.mkdirs()
        }
        if (dirExists) {
            file = File(root, fileName(context))
        }
    } else {
        Timber.e("External storage not available")
    }
    return file
}

/* Helper method to determine if external storage is available*/
internal fun isExternalStorageAvailable(): Boolean {
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}

fun sendLogsViaEmail(context: Context, emailAddress: String) {
    val root = File(context.getExternalFilesDir(null)!!.toURI())
    val file = File(root, fileName(context))
    val path = Uri.fromFile(file)
    val emailIntent = Intent(Intent.ACTION_SEND)
    // set the type to 'email'
    emailIntent.type = "vnd.android.cursor.dir/email"
    val to = arrayOf(emailAddress)
    emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
    // the attachment
    emailIntent.putExtra(Intent.EXTRA_STREAM, path)
    // the mail subject
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[LOGS] ${context.appName()}")
    context.startActivity(Intent.createChooser(emailIntent, "Send logs via email"))
}

fun deleteLogsFile(context: Context) {
    val root = File(context.getExternalFilesDir(null)!!.toURI())
    val file = File(root, fileName(context))
    if (file.exists())
        file.delete()
}

fun openLogsFile(context: Context) {
    val root = File(context.getExternalFilesDir(null)!!.toURI())
    val file = File(root, fileName(context))
    if (file.exists()) {
        val mime = "text/html"
        // Open file with user selected app
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), mime)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}
