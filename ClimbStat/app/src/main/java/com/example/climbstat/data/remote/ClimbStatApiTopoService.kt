package com.example.climbstat.data.remote

import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.data.remote.topo.AddTopoResponse
import com.example.climbstat.data.remote.topo.TopoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ClimbStatApiTopoService {
    @GET("topo/me")
    suspend fun getMyTopos(
        @Header("Authorization") userToken: String,
    ): List<TopoDto>

    @GET("boulder/{boulderId}/topo")
    suspend fun getToposByBoulderId(
        @Header("Authorization") userToken: String,
        @Path("boulderId") boulderId: String
    ): List<TopoDto>

    @POST("boulder/{boulderId}/topo")
    suspend fun addTopo(
        @Header("Authorization") userToken: String,
        @Path("boulderId") boulderId: String,
        @Body body: AddTopoRequest
    ): AddTopoResponse
}