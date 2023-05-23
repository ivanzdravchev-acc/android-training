package com.example.androidtrainingproject.models

data class LoginRequestBody (val identifier: String, val password: String)

data class LoginResponse(val jwt: String, val user: User)

data class User(
    val id: Number,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean
)