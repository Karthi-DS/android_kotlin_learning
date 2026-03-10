package com.example.first_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

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

        otpReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val msg = intent?.getStringExtra("msg_body").toString()
                Log.v("msg",msg)
                ed1.setText(msg)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        registerReceiver(otpReceiver, IntentFilter("SMS_RECEIVED"), RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(otpReceiver)
    }
}