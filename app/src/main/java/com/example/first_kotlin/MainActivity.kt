package com.example.first_kotlin

import android.content.res.Resources
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gender = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,gender)
        val dropdown = findViewById<Spinner>(R.id.gender)
        dropdown.adapter = adapter;
    }
}
