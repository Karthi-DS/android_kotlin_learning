package com.example.first_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.first_kotlin.R.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val btn1 = findViewById<Button>(id.btn1)
        val btn2 = findViewById<Switch>(id.btn2)
        val btn3 = findViewById<Button>(id.btn3)
        val btn4 = findViewById<Button>(id.btn4)

        btn1.setOnClickListener {
            val intent = Intent(this, message::class.java).also{
                startActivity(it)
            }
        }

        btn2.setOnCheckedChangeListener {_,isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }else{
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        btn3.setOnClickListener {
            Intent(this, MusicActivity::class.java).also{
                startActivity(it)
            }
        }

        btn4.setOnClickListener {
            Intent(this, FormActivity::class.java).also{
                startActivity(it)
            }
        }

    }
}
