package com.hanseltritama.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class OrderedReceiver2 : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var resultCode = resultCode
        var resultData: String = resultData
        var resultExtras: Bundle = getResultExtras(true)
        var stringExtra: String? = resultExtras .getString("stringExtra")

        resultCode += 1
        stringExtra += "->OR2"

        val toastText: String = "OR2\n" +
                "resultCode $resultCode\n" +
                "resultData $resultData\n" +
                "stringExtra $stringExtra"


        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()

        resultData = "OR2"
        resultExtras.putString("stringExtra", stringExtra)

        setResult(resultCode, resultData, resultExtras)

        abortBroadcast()
    }

}