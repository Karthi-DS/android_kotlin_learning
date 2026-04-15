package com.example.first_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstForm = Form1()
        val secondForm = Form2()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstForm)
            addToBackStack(null)
            commit()
        }



    }



}
