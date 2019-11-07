package com.dci.dev.ktimber

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class DebugLogTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        var priority = priority
        // Workaround for devices that doesn't show lower priority logs
        when {
            Build.MANUFACTURER == "HUAWEI" || Build.MANUFACTURER == "samsung" ->
                if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
                    priority = Log.ERROR
        }
        super.log(priority, tag, message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + "@" + element.lineNumber
    }
}

class ReleaseLogTree(private val minLogLevel: Int = Log.WARN) : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= minLogLevel
    }

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {

            // Message is short enough, doesn't need to be broken into chunks
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }

            // Split by line, then ensure each line can fit into Log's max length
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newline)
                i++
            }
        }
    }

    companion object {

        private const val MAX_LOG_LENGTH = 4000
    }
}

class FileLoggingTree(private val file: File) : Timber.DebugTree() {

    private val logTag = FileLoggingTree::class.java.simpleName

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {
            val logTimeStamp = SimpleDateFormat(
                "yyy-MM-dd' | 'HH:mm:ss:SSS",
                Locale.getDefault()
            ).format(Date())

            val color =  when (priority) {
                Log.VERBOSE -> "#424242"
                Log.DEBUG -> "#2196F3"
                Log.INFO -> "#4CAF50"
                Log.WARN -> "#FFC107"
                Log.ERROR-> "#F44336"
                Log.ASSERT -> "#9C27B0"
                else -> "#FFAB00"
            }

            val priorityTag = when (priority) {
                Log.VERBOSE -> "VERBOSE"
                Log.DEBUG -> "DEBUG"
                Log.INFO -> "INFO"
                Log.WARN -> "WARN"
                Log.ERROR-> "ERROR"
                Log.ASSERT -> "ASSERT"
                else -> "WTF"
            }

            // If file created or exists save logs
            val writer = FileWriter(file, true)
            writer.append("<p style=\"color:$color;\"><strong>")
                .append(logTimeStamp)
                .append("</strong><strong>")
                .append(" | $tag")
                .append(" | $priorityTag | ")
                .append("</strong> ")
                .append(message)
                .append("</p>")
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            Timber.e(logTag, "Error while logging into file : $e")
        }
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + "@" + element.lineNumber
    }
}
