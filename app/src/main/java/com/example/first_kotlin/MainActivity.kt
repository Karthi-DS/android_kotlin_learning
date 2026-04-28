package com.example.first_kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_kotlin.data.AppNotification
import com.example.first_kotlin.data.User
import com.example.first_kotlin.firebase_firestore.FireStoreClient
import com.example.first_kotlin.notifications.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val fireStoreClient = FireStoreClient()
    private lateinit var userAdapter: UserAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askNotificationPermission()
        setupRecyclerView()

        findViewById<Button>(R.id.btnSaveUser).setOnClickListener {
            addRandomUser()
        }

        findViewById<Button>(R.id.btnPushNotification).setOnClickListener {
            pushAppNotification()
        }

        refreshUsers()
        
        // Start listening for notifications from Firestore
        fireStoreClient.listenForNotifications { notification ->
            NotificationHelper.showNotification(this, notification.title, notification.message)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
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

    private fun refreshUsers() {
        lifecycleScope.launch {
            try {
                val users = fireStoreClient.getAllUsers()
                userAdapter.submitList(users)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching users", e)
            }
        }
    }

    private fun addRandomUser() {
        val randomNum = Random.nextInt(100)
        val newUser = User(
            name = "User $randomNum",
            email = "user$randomNum@example.com"
        )
        lifecycleScope.launch {
            try {
                fireStoreClient.insertUser(newUser)
                refreshUsers()
            } catch (e: Exception) { }
        }
    }

    private fun pushAppNotification() {
        val notification = AppNotification(
            title = "App Alert!",
            message = "Someone clicked the push button at ${System.currentTimeMillis()}"
        )
        lifecycleScope.launch {
            try {
                fireStoreClient.sendNotification(notification)
                Toast.makeText(this@MainActivity, "Notification Sent to All!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error sending notification", e)
            }
        }
    }

    private fun updateUserName(user: User) {
        lifecycleScope.launch {
            try {
                fireStoreClient.updateUser(user.copy(name = user.name + " (Mod)"))
                refreshUsers()
            } catch (e: Exception) { }
        }
    }

    private fun deleteUser(user: User) {
        lifecycleScope.launch {
            try {
                fireStoreClient.deleteUser(user.id)
                refreshUsers()
            } catch (e: Exception) { }
        }
    }
}
