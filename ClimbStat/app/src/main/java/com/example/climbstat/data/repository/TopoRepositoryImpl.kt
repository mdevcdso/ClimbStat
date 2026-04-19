package com.example.climbstat.data.repository

import com.example.climbstat.data.datasource.remote.RemoteTopoDataSource
import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.data.remote.topo.AddTopoResponse
import com.example.climbstat.domain.model.Topo
import com.example.climbstat.domain.repository.TopoRepository
import com.example.climbstat.utils.TokenManagerUtils

class TopoRepositoryImpl(
    val remote: RemoteTopoDataSource,
    val tokenManager: TokenManagerUtils
): TopoRepository{
    private val userToken: String
        get() = tokenManager.getToken().orEmpty()

    override suspend fun getUserTopos(): Result<List<Topo>> {
        val userTopos = remote.getUserTopos(userToken)
        return if (userTopos.isSuccess) {
            val topos = userTopos.getOrThrow()
            Result.success(topos)
        } else {
            Result.failure(Exception("Failed to fetch user topos: ${userTopos.exceptionOrNull()?.message}"))
        }
    }

    override suspend fun getToposByBoulderId(boulderId: String): Result<List<Topo>> {
        val boulderTopos = remote.getToposByBoulderId(userToken, boulderId)
        return if (boulderTopos.isSuccess) {
            val topos = boulderTopos.getOrThrow()
            Result.success(topos)
        } else {
            Result.failure(Exception("Failed to fetch topos for the boulder: ${boulderTopos.exceptionOrNull()?.message}"))
        }
    }

    override suspend fun addTopo(
        boulderId: String,
        topoBody: AddTopoRequest
    ): Result<AddTopoResponse> {
        val topoResponse = remote.addTopo(userToken, boulderId, topoBody)
        return if (topoResponse.isSuccess) {
            val response = topoResponse.getOrThrow()
            Result.success(response)
        } else {
            Result.failure(Exception("Failed to add topo: ${topoResponse.exceptionOrNull()?.message}"))
        }
    }

}