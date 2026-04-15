package com.example.climbstat.data.datasource.remote

import com.example.climbstat.data.remote.ClimbStatApiTopoService
import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.data.remote.topo.AddTopoResponse
import com.example.climbstat.data.remote.topo.toDomainModel
import com.example.climbstat.domain.model.Topo

class RemoteTopoDataSource(
    private val apiService: ClimbStatApiTopoService
){
    suspend fun getUserTopos(userToken: String): Result<List<Topo>>{
        return try {
            val response = this.apiService.getMyTopos(
                userToken = "Bearer $userToken"
            )
            Result.success(
                response.map{
                    it.toDomainModel()
                }
            )
        }catch ( e: Exception){
            Result.failure(e)
         }
    }

    suspend fun getToposByBoulderId(userToken: String, boulderId: String): Result<List<Topo>>{
        return try {
            val response = this.apiService.getToposByBoulderId(
                userToken = "Bearer $userToken",
                boulderId = boulderId
            )
            Result.success(
                response.map{
                    it.toDomainModel()
                }
            )
        }catch ( e: Exception){
            Result.failure(e)
        }
    }

    suspend fun addTopo(userToken: String, boulderId: String, body: AddTopoRequest): Result<AddTopoResponse> {
        return try {
            val response = this.apiService.addTopo(
                userToken = "Bearer $userToken",
                boulderId = boulderId,
                body = body
            )
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}