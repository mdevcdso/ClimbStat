package com.example.climbstat.data.local.climbingGym


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "climbing_gym")
data class ClimbingGymEntity(
    @PrimaryKey
    val id: String,
    val address: String,
    val closingHours: String,
    val cotationType: String,
    val createdAt: String,
    val description: String,
    val franchise: String,
    val image: String,
    val location: String,
    val name: String,
    val openingHours: String,
    val tags: String,
    val updatedAt: String
)