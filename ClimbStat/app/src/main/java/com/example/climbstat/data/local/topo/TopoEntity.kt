package com.example.climbstat.data.local.topo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topo")
data class TopoEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val userName: String,
    val attemptDate: String,
    val idBoulder: String,
    val boulderDifficulty: String?,
    val boulderGymId: String?,
    val isFlash: Boolean,
    val nbAttempts: Int,
    val comment: String
)