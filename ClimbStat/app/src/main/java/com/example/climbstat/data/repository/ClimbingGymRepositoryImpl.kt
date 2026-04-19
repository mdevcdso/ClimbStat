package com.example.climbstat.data.repository

import android.util.Log
import com.example.climbstat.data.datasource.remote.RemoteClimbingGymDataSource
import com.example.climbstat.data.local.climbingGym.ClimbingGymDao
import com.example.climbstat.data.local.climbingGym.toDomain
import com.example.climbstat.data.local.climbingGym.toEntity
import com.example.climbstat.data.remote.climbingGym.toDomainModel
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.utils.TokenManagerUtils
import kotlinx.coroutines.flow.first

class ClimbingGymRepositoryImpl(
    val remote: RemoteClimbingGymDataSource,
    val local: ClimbingGymDao,
    val tokenManager: TokenManagerUtils
) : ClimbingGymRepository {
    private val userToken: String
        get() = tokenManager.getToken().orEmpty()

    override suspend fun fetchClimbingGyms(): Result<List<ClimbingGym>> {
        val fetchResult = remote.getClimbingGyms(userToken)
        return if (fetchResult.isSuccess) {
            val gyms = fetchResult.getOrThrow()
            local.clearAll()
            gyms.forEach { local.insertGym(it.toEntity()) }
            Result.success(gyms)
        } else {
            try {
                val localData = local.getAllGyms().first()
                val gyms = localData.map { it.toDomain() }
                if (gyms.isNotEmpty()) {
                    Result.success(gyms)
                } else {
                    Result.failure(Exception("No local data available + ${fetchResult.exceptionOrNull()?.message}"))
                }
            } catch (e: Exception) {
                Result.failure( Exception("Failed local + remote: ${e.message}"))
            }
        }
    }

    override suspend fun fetchClimbingGymById(id: String): Result<ClimbingGym> {
        val fetchResult = remote.getClimbingGymById(userToken, id)
        return if (fetchResult.isSuccess) {
            val climbingGym = fetchResult.getOrThrow()
            Result.success(climbingGym)
        } else {
            Result.failure(Exception("Failed to fetch climbing gym by id: ${fetchResult.exceptionOrNull()?.message}"))
        }
    }
}