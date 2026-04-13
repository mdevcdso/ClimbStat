package com.example.climbstat.data.repository

import android.util.Log
import com.example.climbstat.data.datasource.remote.RemoteClimbingGymDataSource
import com.example.climbstat.data.remote.climbingGym.toDomainModel
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.utils.TokenManagerUtils

class ClimbingGymRepositoryImpl(
    val remote: RemoteClimbingGymDataSource,
    val tokenManager: TokenManagerUtils
): ClimbingGymRepository {
    val userToken: String = tokenManager.getToken().toString()

    override suspend fun fetchClimbingGyms(): Result<List<ClimbingGym>> {
        //Log.e("TestClimbingGym", "UserToke : $userToken")
        val fetchResult = remote.getClimbingGyms(userToken)
        return if (fetchResult.isSuccess) {
            val climbingGyms = fetchResult.getOrThrow()
            Result.success(climbingGyms)
        } else {
            Result.failure(Exception("Failed to fetch climbing gyms: ${fetchResult.exceptionOrNull()?.message}"))
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