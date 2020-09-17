package com.hanseltritama.broadcastreceiver

import android.Manifest
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val orderedReceiver1: OrderedReceiver1 = OrderedReceiver1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter("com.hanseltritama.MY_ACTION")
        intentFilter.priority = 1
        registerReceiver(orderedReceiver1, intentFilter, Manifest.permission.VIBRATE, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(orderedReceiver1)
    }
}
