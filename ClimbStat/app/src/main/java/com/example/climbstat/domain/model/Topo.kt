package com.example.climbstat.domain.model

data class Topo(
    val id: String,
    val userId: String,
    val userName: String,
    val attemptDate: String,
    val idBoulder: String,
    val isFlash: Boolean,
    val nbAttempts: Int
)