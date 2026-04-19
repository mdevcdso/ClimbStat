package com.example.climbstat.domain.model

data class GymStats(
    val gymId: String,
    val gymName: String,
    val totalTops: Int,
    val flashRate: Float,
    val topsByTier: Map<DifficultyTier, Int>
)
