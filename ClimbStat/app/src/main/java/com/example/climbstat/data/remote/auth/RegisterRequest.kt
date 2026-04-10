package com.example.climbstat.data.remote.auth

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)