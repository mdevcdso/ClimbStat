package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.repository.TopoRepository

class FetchBoulderTopos(
    val repository: TopoRepository
){
    suspend operator fun invoke(boulderId: String): ToposUiState {
        val result = repository.getToposByBoulderId(boulderId)
        if (result.isSuccess) return ToposUiState.Success(result.getOrThrow())
        return ToposUiState.Error(message = result.exceptionOrNull()?.message ?: "Failed to fetch topos")
    }
}