package com.example.climbstat.domain.model

data class Boulder(
    val id: String,
    val description: String,
    val difficulty: String,
    val idGym: String,
    val image: String,
    val types: List<String>,
    val createdAt: String
)