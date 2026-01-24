package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val btn = findViewById<Button>(R.id.btn2)
        btn.setOnClickListener {
            finish()
        }
        val btn3 = findViewById<Button>(R.id.btn3)
        btn3.setOnClickListener {
            Intent(this, ThirdActivity::class.java).also({
                startActivity(it)
            })
        }
    }
}