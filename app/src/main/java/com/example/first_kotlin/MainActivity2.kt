package com.example.first_kotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*;

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textInfo = findViewById<TextView>(R.id.textInfo);
        val name = intent.getStringExtra("name")
        val rollNo = intent.getStringExtra("rollNo")
        val gender = intent.getStringExtra("gender")
        val dept = intent.getStringExtra("dept")
        val info = "your name is "+ name + "\n" + " your roll no is " + rollNo + "\n you are from " +dept

        textInfo.text = info
    }
}