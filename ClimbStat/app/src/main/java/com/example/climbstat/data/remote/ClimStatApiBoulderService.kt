package com.example.climbstat.data.remote

import com.example.climbstat.data.remote.boulder.BoulderDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ClimStatApiBoulderService {
    @GET("{climbingGym/boulderId}/boulder")
    suspend fun fetchBouldersByClimbingGymId(
        @Header("Authorization") userToken: String,
        @Path("climbingGymId") climbingGymId: String
    ): List<BoulderDto>

}