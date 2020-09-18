# Broadcast Receiver

## Static Receiver (branch: `static-receiver`)
Put all receivers on `AndroidManifest.xml` file.
```
<receiver android:name=".MyBroadcastReceiver" android:enabled="true" android:exported="true">
    <intent-filter>
        <category android:name="android.intent.category.DEFAULT"/>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
        <action android:name="android.intent.action.QUICKBOOT_POWERON" />
    </intent-filter>
</receiver>
```
Execute it on `MyBroadcastReceiver.kt` file.
```
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Toast.makeText(context, "Boot Completed!", Toast.LENGTH_LONG).show()
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            Toast.makeText(context, "Connectivity Changed!", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## Dynamic Receiver (branch: `dynamic-receiver`)
Put receiver on `Activity` or `Fragment` lifecycle.
```
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
```
It’s important to unregister your `BroadcastReceiver` in the right cycle. For example: 

* If you register it on `onStart()`, unregister it on `onStop()`
* If you register it on `onResume()`, unregister it on `onPause()`
* If you register it on `onCreate()`, unregister it on `onDestroy()`

## Custom Broadcast (branch: `custom-broadcast`)
Customize your own broadcast `intent filter` and `action`.
* Add `BroadcastReceiver` file to the receiver:
```
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if ("com.hanseltritama.MY_ACTION" == intent?.action) {
            val receivedText: String = intent.getStringExtra("com.hanseltritama.MY_TEXT")
            Toast.makeText(context, receivedText, Toast.LENGTH_SHORT).show()
        }
    }

}
```
* Register it on the `Activity` or `Fragment`.
```
class MainActivity : AppCompatActivity() {

    val myBroadcastReceiver: MyBroadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: IntentFilter = IntentFilter("com.hanseltritama.MY_ACTION")
        registerReceiver(myBroadcastReceiver, intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
    }
}
```
* Request it on the [sender file](https://github.com/hanselgunawan/BroadcastSender).
  
### 1st Approach
```
val intent = Intent("com.hanseltritama.MY_ACTION")
val cn = ComponentName("com.hanseltritama.broadcastreceiver", "com.hanseltritama.broadcastreceiver.MyBroadcastReceiver")
intent.component = cn
sendBroadcast(intent)
```
### 2nd Approach
```
val intent = Intent("com.hanseltritama.MY_ACTION")
intent.setClassName("com.hanseltritama.broadcastreceiver", "com.hanseltritama.broadcastreceiver.MyBroadcastReceiver")
sendBroadcast(intent)
```
### 3rd Approach
```
val intent = Intent("com.hanseltritama.MY_ACTION")
intent.setPackage("com.hanseltritama.broadcastreceiver")
sendBroadcast(intent)
```
### 4th Approach
```
val intent = Intent("com.hanseltritama.MY_ACTION")
val packageManager: PackageManager = packageManager

// check which app that has a BroadcastReceiver that is registered
// with an intent-filter com.hansel.MY_ACTION
// Let the system found its receiver
val infos: List<ResolveInfo> = packageManager.queryBroadcastReceivers(intent, 0)
for (info in infos) {
    val cn = ComponentName(info.activityInfo.packageName,
        info.activityInfo.name)
    intent.component = cn
    sendBroadcast(intent)
}
```
## Ordered Broadcast (branch: `ordered-broadcast`)
Set `android:priority` on the receiver:
```
<receiver android:name=".OrderedReceiver2">
    <intent-filter android:priority="2">
        <action android:name="com.hanseltritama.MY_ACTION" />
    </intent-filter>
</receiver>
<receiver android:name=".OrderedReceiver3">
    <intent-filter android:priority="3">
        <action android:name="com.hanseltritama.MY_ACTION" />
    </intent-filter>
</receiver>
```
```
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
```
It will be executed from the highest to lowest priority number.
## Broadcast Permissions (branch: `broadcast-permission`)
Add permissions on `<receiver>` tag on `AndroidManifest.xml`.
```
<receiver android:name=".OrderedReceiver2"
    android:permission="android.permission.INTERNET">
    <intent-filter android:priority="2">
        <action android:name="com.hanseltritama.MY_ACTION" />
    </intent-filter>
</receiver>
<receiver android:name=".OrderedReceiver3"
    android:permission="com.hanseltritama.CUSTOM_PERMISSION">
    <intent-filter android:priority="3">
        <action android:name="com.hanseltritama.MY_ACTION" />
    </intent-filter>
</receiver>
```
Add `uses-permission` on sender's `AndroidManifest.xml` file.
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="com.hanseltritama.CUSTOM_PERMISSION" />
```
## goAsync() (branch: `go-async`)
This is used to run a heavy work calculation inside the receiver.
```
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
```
