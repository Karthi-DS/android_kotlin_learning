package com.example.first_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_kotlin.data.User
import com.example.first_kotlin.firebase_firestore.FireStoreClient
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val fireStoreClient = FireStoreClient()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        val btnSaveUser = findViewById<Button>(R.id.btnSaveUser)
        btnSaveUser.setOnClickListener {
            addRandomUser()
        }

        observeUsers()
    }

    private fun setupRecyclerView() {
        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)
        userAdapter = UserAdapter(
            onEdit = { user -> updateUserName(user) },
            onDelete = { user -> deleteUser(user) }
        )
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = userAdapter
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            fireStoreClient.getAllUsers().collect { result ->
                result.onSuccess { users ->
                    userAdapter.submitList(users)
                }.onFailure { e ->
                    Toast.makeText(this@MainActivity, "Error loading users: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addRandomUser() {
        val randomNum = Random.nextInt(100)
        val newUser = User(
            name = "User $randomNum",
            email = "user$randomNum@example.com",
            password = "pass$randomNum"
        )

        lifecycleScope.launch {
            fireStoreClient.insertUser(newUser).collect { result ->
                result.onSuccess { 
                    Toast.makeText(this@MainActivity, "User Added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUserName(user: User) {
        val updatedUser = user.copy(name = user.name + " (Updated)")
        lifecycleScope.launch {
            fireStoreClient.updateUser(updatedUser).collect { result ->
                result.onSuccess { 
                    Toast.makeText(this@MainActivity, "User Updated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteUser(user: User) {
        lifecycleScope.launch {
            fireStoreClient.deleteUser(user.id).collect { result ->
                result.onSuccess { 
                    Toast.makeText(this@MainActivity, "User Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
