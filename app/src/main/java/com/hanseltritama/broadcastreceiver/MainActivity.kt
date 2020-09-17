package com.hanseltritama.broadcastreceiver

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val orderedReceiver1: OrderedReceiver1 = OrderedReceiver1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter("com.hanseltritama.MY_ACTION")
        intentFilter.priority = 1
        registerReceiver(orderedReceiver1, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(orderedReceiver1)
    }
}
