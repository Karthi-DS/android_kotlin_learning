package com.example.first_kotlin.data.remote

import com.example.first_kotlin.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("todos")
    suspend fun  getUsers(): List<User>
}

