package com.example.first_kotlin

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btnToast)
        btn.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.custom_toast,findViewById<ConstraintLayout>(R.id.customToast));
            val toast = Toast(this@MainActivity)
                toast.view = layout
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
        }
    }
}
