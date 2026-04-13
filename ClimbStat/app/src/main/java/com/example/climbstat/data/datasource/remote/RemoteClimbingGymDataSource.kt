package com.example.climbstat.data.datasource.remote

import com.example.climbstat.data.remote.ClimbStatApiGymService
import com.example.climbstat.data.remote.climbingGym.ClimbingGymDto

class RemoteClimbingGymDataSource(
    private val apiService: ClimbStatApiGymService
) {
    suspend fun getClimbingGyms(userToken: String): Result<List<ClimbingGymDto>> {
        return try {
            val response = this.apiService.fetchClimbingGyms(
                userToken = userToken
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getClimbingGymById(userToken: String, id: String): Result<ClimbingGymDto> {
        return try {
            val response = this.apiService.fetchClimbingGymById(
                userToken = userToken,
                id = id
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}