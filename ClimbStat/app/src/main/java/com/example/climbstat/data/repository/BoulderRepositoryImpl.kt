package com.example.climbstat.data.repository

import com.example.climbstat.data.datasource.remote.RemoteBoulderDataSource
import com.example.climbstat.data.local.boulder.BoulderDao
import com.example.climbstat.data.local.boulder.toDomain
import com.example.climbstat.data.local.boulder.toEntity
import com.example.climbstat.domain.repository.BoulderRepository
import com.example.climbstat.utils.TokenManagerUtils
import kotlinx.coroutines.flow.map

class BoulderRepositoryImpl (
    val remote: RemoteBoulderDataSource,
    val local: BoulderDao,
    val tokenManager: TokenManagerUtils
): BoulderRepository{
    private val userToken: String
        get() = tokenManager.getToken().orEmpty()

    override suspend fun fetchBouldersByGymId(gymId: String): Result<List<com.example.climbstat.domain.model.Boulder>> {
        val fetchResult = remote.getBouldersByClimbingGymId(userToken, gymId)
        return if (fetchResult.isSuccess) {
            val boulders = fetchResult.getOrThrow()
            local.clearAll()
            boulders.forEach { local.insertBoulder(it.toEntity()) }
            Result.success(boulders)
        } else {
            try{
                val localData = local.getBouldersByGymId(gymId)
                val boulders = localData.map { it.toDomain() }
                if(boulders.isNotEmpty()){
                    Result.success(boulders)
                }else{
                    Result.failure(Exception("No local data available for boulders with gym id: $gymId + ${fetchResult.exceptionOrNull()?.message}"))
                }
            }catch (e: Exception) {
                Result.failure(Exception("Failed to fetch boulders by gym id: ${fetchResult.exceptionOrNull()?.message}"))
            }
        }
    }
}