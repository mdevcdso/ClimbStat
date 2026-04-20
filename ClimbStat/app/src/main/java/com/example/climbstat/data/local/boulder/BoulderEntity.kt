package com.example.climbstat.data.local.boulder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boulder")
data class BoulderEntity(

    @PrimaryKey
    val id: String,

    val description: String,
    val difficulty: String,
    val idGym: String,
    val image: String,

    val types: String,

    val createdAt: String
)
