package com.example.climbstat.domain.model

data class User(
    val token: String,
    val id: String,
    val email: String,
    val name: String,
    val role: String
)