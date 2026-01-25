package com.example.first_kotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0)
        val state = intent.getStringExtra("state")

        val resultMsg = "Hello your name is " + name + " your age is "+ age + " and you're from "+ state
        val msg = findViewById<TextView>(R.id.msg)
        msg.text = resultMsg
    }
}