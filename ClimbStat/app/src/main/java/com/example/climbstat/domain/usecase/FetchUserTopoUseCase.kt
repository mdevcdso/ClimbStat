package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.TopoRepository
import com.example.climbstat.domain.usecase.state.ToposUiState

class FetchUserTopoUseCase(
    val repository: TopoRepository
) {
    suspend operator fun invoke(): ToposUiState {
        val result = repository.getUserTopos()
        if (result.isSuccess) return ToposUiState.Success(result.getOrThrow())
        return ToposUiState.Error(message = result.exceptionOrNull()?.message ?: "Failed to fetch topos")
    }
}