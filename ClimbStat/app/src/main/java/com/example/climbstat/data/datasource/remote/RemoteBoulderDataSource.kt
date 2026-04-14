package com.example.climbstat.data.datasource.remote

import com.example.climbstat.data.remote.ClimStatApiBoulderService
import com.example.climbstat.data.remote.boulder.toDomainModel
import com.example.climbstat.domain.model.Boulder

class RemoteBoulderDataSource(
    private val apiService: ClimStatApiBoulderService
) {
    suspend fun getBouldersByClimbingGymId(userToken: String, climbingGymId: String): Result<List<Boulder>> {
        return try {
            val response = this.apiService.fetchBouldersByClimbingGymId(
                userToken = "Bearer $userToken",
                climbingGymId = climbingGymId
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
}