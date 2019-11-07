package com.dci.dev.ktimber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        KTimber.startWithFileLogger(applicationContext)

        var i = 0

        // region assert
        btn_logs_a_m.setOnClickListener {
            logAssert(message(i))
            i++
        }
        btn_logs_a_t_m.setOnClickListener {
            logAssert(error(i), message(i))
            i++
        }
        btn_logs_a_t.setOnClickListener {
            logAssert(error(i))
            i++
        }
        // endregion

        // region info
        btn_logs_i_m.setOnClickListener {
            logInfo(message(i))
            i++
        }
        btn_logs_i_t_m.setOnClickListener {
            logInfo(error(i), message(i))
            i++
        }
        btn_logs_i_t.setOnClickListener {
            logInfo(error(i))
            i++
        }
        // endregion

        // region verbose
        btn_logs_v_m.setOnClickListener {
            logVerbose(message(i))
            i++
        }
        btn_logs_v_t_m.setOnClickListener {
            logVerbose(error(i), message(i))
            i++
        }
        btn_logs_v_t.setOnClickListener {
            logVerbose(error(i))
            i++
        }
        // endregion

        // region warn
        btn_logs_w_m.setOnClickListener {
            logWarn(message(i))
            i++
        }
        btn_logs_w_t_m.setOnClickListener {
            logWarn(error(i), message(i))
            i++
        }
        btn_logs_w_t.setOnClickListener {
            logWarn(error(i))
            i++
        }
        // endregion

        // region debug
        btn_logs_d_m.setOnClickListener {
            logDebug(message(i))
            i++
        }
        btn_logs_d_t_m.setOnClickListener {
            logDebug(error(i), message(i))
            i++
        }
        btn_logs_d_t.setOnClickListener {
            logDebug(error(i))
            i++
        }
        // endregion

        // region error
        btn_logs_e_m.setOnClickListener {
            logError(message(i))
            i++
        }
        btn_logs_e_t_m.setOnClickListener {
            logError(error(i), message(i))
            i++
        }
        btn_logs_e_t.setOnClickListener {
            logError(error(i))
            i++
        }
        // endregion

        // region functional buttons
        btn_send.setOnClickListener {
            sendLogsViaEmail(applicationContext, "dita.cristian.ionut@gmail.com")
        }

        btn_view.setOnClickListener {
            openLogsFile(applicationContext)
        }

        btn_delete.setOnClickListener {
            deleteLogsFile(applicationContext)
        }
        // endregion

    }

    private fun error(index: Int) = Throwable("Throwable -> $index")
    private fun message(index: Int) = "Message -> $index"
}
