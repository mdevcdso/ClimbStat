package com.example.climbstat.domain.usecase.state

import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.data.remote.topo.AddTopoResponse
import com.example.climbstat.domain.repository.TopoRepository

class AddNewTopoUseCase (
    val repository: TopoRepository
){
    suspend operator fun invoke(boulderId: String, body: AddTopoRequest): AddTopoResponse? {
        val result = repository.addTopo(boulderId, body)
        if (result.isSuccess) return result.getOrThrow()
        return null
    }
}