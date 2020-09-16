package com.hanseltritama.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            Toast.makeText(context, "Boot Completed!", Toast.LENGTH_LONG).show()
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            Toast.makeText(context, "Connectivity Changed!", Toast.LENGTH_SHORT).show()
        }
    }

}