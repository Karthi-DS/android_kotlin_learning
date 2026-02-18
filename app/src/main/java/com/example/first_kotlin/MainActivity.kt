package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val msg = findViewById<EditText>(R.id.msg).toString()
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            Intent(this, Contacts::class.java).also {
                startActivity(it)
            }
        }
    }
}
