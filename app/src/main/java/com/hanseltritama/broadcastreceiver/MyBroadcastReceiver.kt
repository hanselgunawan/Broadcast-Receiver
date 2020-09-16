package com.hanseltritama.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Toast.makeText(context, "Boot Completed!", Toast.LENGTH_LONG).show()
            Log.d("HANSELA", "HAHAHAHA")
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            Toast.makeText(context, "Connectivity Changed!", Toast.LENGTH_SHORT).show()
        }
    }

}