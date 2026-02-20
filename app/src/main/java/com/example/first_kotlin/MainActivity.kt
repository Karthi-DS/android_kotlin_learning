package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.msg)
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            val msg = editText.text.toString()
            Intent(this, Contacts::class.java).also {
                it.putExtra("msg",msg)
                startActivity(it)
            }
        }
    }
}
