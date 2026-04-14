package com.example.climbstat.data.datasource.remote

import android.util.Log
import com.example.climbstat.data.remote.ClimbStatApiGymService
import com.example.climbstat.data.remote.climbingGym.ClimbingGymDto
import com.example.climbstat.data.remote.climbingGym.toDomainModel
import com.example.climbstat.domain.model.ClimbingGym

class RemoteClimbingGymDataSource(
    private val apiService: ClimbStatApiGymService
) {
    suspend fun getClimbingGyms(userToken: String): Result<List<ClimbingGym>> {
        return try {
            val response = this.apiService.fetchClimbingGyms(
                userToken = "Bearer $userToken"
            )
            Result.success(
                response.map{
                    it.toDomainModel()
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getClimbingGymById(userToken: String, id: String): Result<ClimbingGym> {
        return try {
            val response = this.apiService.fetchClimbingGymById(
                userToken = "Bearer $userToken",
                id = id
            )
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}