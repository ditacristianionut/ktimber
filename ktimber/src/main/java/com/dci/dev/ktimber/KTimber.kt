package com.dci.dev.ktimber

import android.content.Context
import timber.log.Timber

object KTimber {

    private var tree: Timber.Tree? = null

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

    fun start(context: Context, minimumDebugLevel: Int) {
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
            Timber.tag(this::class.java.simpleName)
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
fun logAssert(message: String) {
    if (KTimber.isInitialised()) {
        Timber.wtf(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an assert exception and a message.  */
fun logAssert(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.wtf(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an assert exception.  */
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
fun logError(message: String) {
    if (KTimber.isInitialised()) {
        Timber.e(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an error exception and a message.  */
fun logError(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.e(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log an error exception.  */
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
fun logWarn(message: String) {
    if (KTimber.isInitialised()) {
        Timber.w(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a warning exception and a message.  */
fun logWarn(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.w(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a warning exception.  */
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
fun logInfo(message: String) {
    if (KTimber.isInitialised()) {
        Timber.i(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception and a message.  */
fun logInfo(t: Throwable,message: String) {
    if (KTimber.isInitialised()) {
        Timber.i(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception.  */
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
fun logDebug(message: String) {
    if (KTimber.isInitialised()) {
        Timber.d(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a debug exception and a message.  */
fun logDebug(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.d(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a debug exception.  */
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
fun logVerbose(message: String) {
    if (KTimber.isInitialised()) {
        Timber.v(message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception and a message.  */
fun logVerbose(t: Throwable, message: String) {
    if (KTimber.isInitialised()) {
        Timber.v(t, message)
    } else {
        KTimber.logNotInitialised()
    }
}

/** Log a verbose exception.  */
fun logVerbose(t: Throwable) {
    if (KTimber.isInitialised()) {
        Timber.v(t)
    } else {
        KTimber.logNotInitialised()
    }
}
// endregion

