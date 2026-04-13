package com.example.climbstat.data.remote

import com.example.climbstat.data.remote.climbingGym.ClimbingGymDto
import retrofit2.http.GET

interface ClimbStatApiGymService {
    @GET("climbingGym")
    suspend fun fetchClimbingGyms(): List<ClimbingGymDto>

    @GET("climbingGym/{id}")
    suspend fun fetchClimbingGymById(
        id: String
    ): ClimbingGymDto
}