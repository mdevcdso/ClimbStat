package com.example.climbstat.data.repository

import com.example.climbstat.data.datasource.remote.RemoteBoulderDataSource
import com.example.climbstat.domain.repository.BoulderRepository
import com.example.climbstat.utils.TokenManagerUtils

class BoulderRepositoryImpl (
    val remote: RemoteBoulderDataSource,
    val tokenManager: TokenManagerUtils
): BoulderRepository{
    private val userToken: String
        get() = tokenManager.getToken().orEmpty()

    override suspend fun fetchBouldersByGymId(gymId: String): Result<List<com.example.climbstat.domain.model.Boulder>> {
        val fetchResult = remote.getBouldersByClimbingGymId(userToken, gymId)
        return if (fetchResult.isSuccess) {
            val boulders = fetchResult.getOrThrow()
            Result.success(boulders)
        } else {
            Result.failure(Exception("Failed to fetch boulders by gym id: ${fetchResult.exceptionOrNull()?.message}"))
        }
    }
}