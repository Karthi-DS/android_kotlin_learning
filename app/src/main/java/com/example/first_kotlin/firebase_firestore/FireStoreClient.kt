package com.example.first_kotlin.firebase_firestore

import com.example.first_kotlin.data.AppNotification
import com.example.first_kotlin.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FireStoreClient {
    private val usersCollection = "users"
    private val notificationsCollection = "notifications"
    private val db = FirebaseFirestore.getInstance()

    // --- User CRUD ---

    suspend fun insertUser(user: User): String {
        val documentReference = db.collection(usersCollection)
            .add(user.toHashMap())
            .await()
        return documentReference.id
    }

    suspend fun getAllUsers(): List<User> {
        val snapshot = db.collection(usersCollection)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toUser() }
    }

    suspend fun updateUser(user: User) {
        db.collection(usersCollection).document(user.id)
            .set(user.toHashMap())
            .await()
    }

    suspend fun updateFcmToken(userId: String, token: String) {
        db.collection(usersCollection).document(userId)
            .update("fcmToken", token)
            .await()
    }

    suspend fun deleteUser(userId: String) {
        db.collection(usersCollection).document(userId)
            .delete()
            .await()
    }

    // --- Notification "Push" Logic ---

    // Send a notification by adding it to Firestore
    suspend fun sendNotification(notification: AppNotification) {
        db.collection(notificationsCollection)
            .add(notification)
            .await()
    }

    // Listen for new notifications in real-time
    fun listenForNotifications(onNotificationReceived: (AppNotification) -> Unit) {
        db.collection(notificationsCollection)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null || snapshot.isEmpty) return@addSnapshotListener
                
                val doc = snapshot.documents[0]
                val notification = doc.toObject(AppNotification::class.java)
                if (notification != null) {
                    // Only show if it's very recent (e.g., within last 10 seconds)
                    // to avoid showing old notifications on app start
                    if (System.currentTimeMillis() - notification.timestamp < 10000) {
                        onNotificationReceived(notification)
                    }
                }
            }
    }

    private fun User.toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "fcmToken" to fcmToken
        )
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toUser(): User? {
        return try {
            User(
                id = id,
                name = getString("name") ?: "",
                email = getString("email") ?: "",
                password = getString("password") ?: "",
                fcmToken = getString("fcmToken") ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
