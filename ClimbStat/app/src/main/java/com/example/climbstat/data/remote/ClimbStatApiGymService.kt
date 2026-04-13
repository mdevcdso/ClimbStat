package com.example.climbstat.data.remote

import com.example.climbstat.data.remote.climbingGym.ClimbingGymDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ClimbStatApiGymService {
    @GET("climbingGym")
    suspend fun fetchClimbingGyms(
        @Header("Authorization") userToken: String,
    ): List<ClimbingGymDto>

    @GET("climbingGym/{id}")
    suspend fun fetchClimbingGymById(
        @Header("Authorization") userToken: String,
        @Path("id") id: String
    ): ClimbingGymDto
}