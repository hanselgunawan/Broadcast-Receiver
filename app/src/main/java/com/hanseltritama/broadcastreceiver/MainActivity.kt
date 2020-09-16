package com.hanseltritama.broadcastreceiver

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val myBroadcastReceiver: MyBroadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // when app is on the foreground
    override fun onStart() {
        super.onStart()
        val intent: IntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myBroadcastReceiver, intent)
    }

    // when app is on the background
    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcastReceiver)
    }
}
