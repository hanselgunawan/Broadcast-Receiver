package com.hanseltritama.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if ("com.hanseltritama.MY_ACTION" == intent?.action) {
            val receivedText: String = intent.getStringExtra("com.hanseltritama.MY_TEXT")
            Toast.makeText(context, receivedText, Toast.LENGTH_SHORT).show()
        }
    }

}