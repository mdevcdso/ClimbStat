package com.example.climbstat.domain.repository

import com.example.climbstat.domain.model.Boulder

interface BoulderRepository {
    suspend fun fetchBouldersByGymId(gymId: String): Result<List<Boulder>>
}