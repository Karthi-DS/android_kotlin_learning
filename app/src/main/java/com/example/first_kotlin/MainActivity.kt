package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            val age = findViewById<EditText>(R.id.age).text.toString().toInt()
            val state = findViewById<EditText>(R.id.state).text.toString()
            Intent(this, SecondActivity::class.java).also{
                it.putExtra("name",name)
                it.putExtra("age",age)
                it.putExtra("state",state)
                startActivity(it)
            }
        }

    }
}
