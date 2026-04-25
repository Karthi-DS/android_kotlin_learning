package com.example.first_kotlin.firebase_firestore

import com.example.first_kotlin.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FireStoreClient {
    private val collection = "users"
    private val db = FirebaseFirestore.getInstance()

    // Create
    fun insertUser(user: User): Flow<Result<String>> = callbackFlow {
        db.collection(collection)
            .add(user.toHashMap())
            .addOnSuccessListener { documentReference ->
                trySend(Result.success(documentReference.id))
            }
            .addOnFailureListener { e ->
                trySend(Result.failure(e))
            }
        awaitClose { }
    }

    // Read (Listen for real-time updates)
    fun getAllUsers(): Flow<Result<List<User>>> = callbackFlow {
        val listener = db.collection(collection)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.failure(e))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val users = snapshot.documents.mapNotNull { doc ->
                        doc.toUser()
                    }
                    trySend(Result.success(users))
                }
            }
        awaitClose { listener.remove() }
    }

    // Update
    fun updateUser(user: User): Flow<Result<Unit>> = callbackFlow {
        if (user.id.isEmpty()) {
            trySend(Result.failure(Exception("User ID is empty")))
            close()
            return@callbackFlow
        }
        db.collection(collection).document(user.id)
            .set(user.toHashMap()) // Using set to overwrite/update
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener { e ->
                trySend(Result.failure(e))
            }
        awaitClose { }
    }

    // Delete
    fun deleteUser(userId: String): Flow<Result<Unit>> = callbackFlow {
        db.collection(collection).document(userId)
            .delete()
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener { e ->
                trySend(Result.failure(e))
            }
        awaitClose { }
    }

    private fun User.toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toUser(): User? {
        return try {
            User(
                id = id, // Firestore Document ID
                name = getString("name") ?: "",
                email = getString("email") ?: "",
                password = getString("password") ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
