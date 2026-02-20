package com.example.first_kotlin.data.repository

import com.example.first_kotlin.data.model.User

class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitClient.api.getUsers()
    }
}