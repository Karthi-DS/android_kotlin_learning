package com.example.first_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.IntentFilter
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var otpReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ed1: EditText = findViewById(R.id.ed1)

        if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                100
            )
        }

        otpReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getStringExtra("msgBody")?.let { otp ->
                    Log.v("received_otp", otp)
                    ed1.setText(otp)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(otpReceiver, IntentFilter("OTP_RECEIVED"), RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(otpReceiver)
    }
}