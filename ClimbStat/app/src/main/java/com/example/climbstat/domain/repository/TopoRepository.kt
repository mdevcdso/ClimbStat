package com.example.climbstat.domain.repository

import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.data.remote.topo.AddTopoResponse
import com.example.climbstat.domain.model.Topo

interface TopoRepository {
    suspend fun getUserTopos(): Result<List<Topo>>
    suspend fun getToposByBoulderId(boulderId: String): Result<List<Topo>>
    suspend fun addTopo(boulderId: String, topoBody: AddTopoRequest): Result<AddTopoResponse>
}