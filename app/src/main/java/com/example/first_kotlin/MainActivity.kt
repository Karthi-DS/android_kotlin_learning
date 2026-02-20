package com.example.first_kotlin

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.first_kotlin.data.repository.RetrofitClient
import com.example.first_kotlin.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            try {
                val users = RetrofitClient.api.getUsers()
                println(users)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
