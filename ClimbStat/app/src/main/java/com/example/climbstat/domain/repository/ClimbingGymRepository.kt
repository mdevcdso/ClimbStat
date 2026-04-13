package com.example.climbstat.domain.repository

import com.example.climbstat.domain.model.ClimbingGym

interface ClimbingGymRepository {
    suspend fun fetchClimbingGyms(): Result<List<ClimbingGym>>
    suspend fun fetchClimbingGymById(id: String): Result<ClimbingGym>
}