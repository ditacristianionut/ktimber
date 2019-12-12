package com.dci.dev.ktimber

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.Keep
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException

@Keep
internal fun fileName(context: Context) = context.appName() + ".html"

/*  Helper method to create file*/
@Keep
internal fun generateFile(context: Context): File? {
    var file: File? = null
    val root = context.filesDir
    var dirExists = true
    if (!root.exists()) {
        dirExists = root.mkdirs()
    }
    if (dirExists) {
        file = File(root, fileName(context))
    }
    return file
}

@Keep
fun shareLogsFile(activity: Activity, emailAddress: String) {
    val context = activity.applicationContext
    try {
        val root = context.filesDir
        val file = File(root, fileName(context))
        if (file.exists()) {
            val authority = "${context.packageName}.com.dci.dev.ktimber"
            val contentUri = FileProvider.getUriForFile(context, authority, file)
            val intent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/html")
                .setSubject("Share logs file")
                .setStream(contentUri)
                .setChooserTitle("${context.appName()} logs")
                .setEmailTo(arrayOf(emailAddress))
                .setSubject("[LOGS] ${context.appName()}")
                .createChooserIntent()
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(Intent.createChooser(intent, "Share logs file"))
        } else {
            Toast.makeText(activity, "Logs file is empty!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
}

@Keep
fun deleteLogsFile(context: Context) {
    try {
        val root = context.filesDir
        val file = File(root, fileName(context))
        if (file.exists())
            file.delete()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
}

@Keep
fun openLogsFile(context: Context) {
    try {
        val root = context.filesDir
        val file = File(root, fileName(context))
        val authority = "${context.packageName}.com.dci.dev.ktimber"
        val contentUri = FileProvider.getUriForFile(context, authority, file)
        if (file.exists()) {
            val mime = "text/html"
            // Open file with user selected app
            val intent = Intent().also {
                it.action = Intent.ACTION_VIEW
                it.setDataAndType(contentUri, mime)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Logs file is empty!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
}
