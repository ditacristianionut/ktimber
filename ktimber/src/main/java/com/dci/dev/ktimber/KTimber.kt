package com.dci.dev.ktimber

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import timber.log.Timber

@Keep
object KTimber {

    private var tree: Timber.Tree? = null

    @JvmStatic
    fun startWithFileLogger(context: Context) {
        val file = generateFile(context)
        if (file != null) {
            tree = FileLoggingTree(file)
            tree?.let{
                Timber.tag(context.appName())
                Timber.plant(it, DebugLogTree())
            }
        } else {
            Timber.e("Failed to create logs file")
        }
    }

    @JvmStatic
    fun start(context: Context, minimumDebugLevel: Int = Log.DEBUG) {
        tree = if (BuildConfig.DEBUG) {
            DebugLogTree()
        } else {
            ReleaseLogTree(minimumDebugLevel)
        }
        tree?.let {
            Timber.tag(context.appName())
            Timber.plant(it)
        }
    }

    internal fun isInitialised(): Boolean {
        if (tree == null) {
            Timber.plant(DebugLogTree())
        }
        return tree != null
    }

    internal fun logNotInitialised() {
        Timber.e("Please initialise KTimber\nCall start() or startWithFileLogger()")
    }

}

// region LEVEL ASSERT
/** Log an assert message. **/
@Keep
fun logAssert(message: String) {
    if (KTimber.isInitialised()) {
        Timber.wtf(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an assert exception and a message.  */
@Keep
fun logAssert(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.wtf(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an assert exception.  */
@Keep
fun logAssert(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.wtf(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

// region LEVEL ERROR
/** Log an error message.  */
@Keep
fun logError(message: String) {
    if (KTimber.isInitialised()) {
        Timber.e(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an error exception and a message.  */
@Keep
fun logError(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.e(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an error exception.  */
@Keep
fun logError(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.e(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

// region LEVEL WARNING
/** Log a warning message.  */
@Keep
fun logWarn(message: String) {
    if (KTimber.isInitialised()) {
        Timber.w(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a warning exception and a message.  */
@Keep
fun logWarn(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.w(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a warning exception.  */
@Keep
fun logWarn(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.w(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

// region LEVEL INFO
/** Log a verbose message.  */
@Keep
fun logInfo(message: String) {
    if (KTimber.isInitialised()) {
        Timber.i(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception and a message.  */
@Keep
fun logInfo(t: Throwable,message: String) {
    if (KTimber.isInitialised()) {
        Timber.i(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception.  */
@Keep
fun logInfo(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.i(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

// region LEVEL DEBUG
/** Log a debug message.  */
@Keep
fun logDebug(message: String) {
    if (KTimber.isInitialised()) {
        Timber.d(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a debug exception and a message.  */
@Keep
fun logDebug(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.d(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a debug exception.  */
@Keep
fun logDebug(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.d(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

// region LEVEL VERBOSE
/** Log a verbose message.  */
@Keep
fun logVerbose(message: String) {
    if (KTimber.isInitialised()) {
        Timber.v(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception and a message.  */
@Keep
fun logVerbose(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.v(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception.  */
@Keep
fun logVerbose(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.v(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

