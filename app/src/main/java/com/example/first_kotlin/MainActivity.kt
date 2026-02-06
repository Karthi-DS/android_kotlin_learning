package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var start: Button
    private lateinit var stop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById<Button>(R.id.start)
        stop = findViewById<Button>(R.id.stop)

        start.setOnClickListener {
            startService(Intent(this, MyService::class.java))
            Toast.makeText(this,"Started Activity",Toast.LENGTH_LONG).show()
        }
        stop.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
            Toast.makeText(this,"Stopped Activity",Toast.LENGTH_LONG).show()
        }
    }
}
