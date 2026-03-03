package com.example.first_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Using a nullable var is safer than lateinit to avoid "Not Initialized" crashes
    private var otpReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupReceiver()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                111
            )
        }
    }

    private fun setupReceiver() {
        val ed = findViewById<EditText>(R.id.ed1)

        otpReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (sms in messages) {
                    val body = sms.displayMessageBody
                    Log.v("sms_received", body)
                    ed.setText(body)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        otpReceiver?.let {
            val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                registerReceiver(it, filter, RECEIVER_EXPORTED)
            } else {
                registerReceiver(it, filter)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // 4. Always unregister to prevent memory leaks
        try {
            otpReceiver?.let { unregisterReceiver(it) }
        } catch (e: Exception) {
            Log.e("Error", "Receiver already unregistered or not found")
        }
    }
}