package com.example.climbstat.domain.model

data class ClimbingGym(
    val id: String,
    val address: String,
    val clossingHours: String,
    val cotationType: String,
    val createdAt: String,
    val description: String,
    val franchise: String,
    val image: String,
    val location: String,
    val name: String,
    val openingHours: String,
    val tags: List<String>,
    val updatedAt: String
)