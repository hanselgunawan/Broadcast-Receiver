package com.hanseltritama.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.widget.Toast

class OrderedReceiver2 : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingResult: PendingResult = goAsync()
        val handler: Handler = Handler()

        Thread(Runnable {
            SystemClock.sleep(10000)

            var resultCode = pendingResult.resultCode
            var resultData: String = pendingResult.resultData
            var resultExtras: Bundle = pendingResult.getResultExtras(true)
            var stringExtra: String? = resultExtras .getString("stringExtra")

            resultCode += 1
            stringExtra += "->OR2"

            val toastText: String = "OR2\n" +
                    "resultCode $resultCode\n" +
                    "resultData $resultData\n" +
                    "stringExtra $stringExtra"

            handler.post {
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }

            pendingResult.resultData = "OR2"
            resultExtras.putString("stringExtra", stringExtra)

            pendingResult.setResult(resultCode, resultData, resultExtras)
            pendingResult.finish()
        }).start()

    }

}