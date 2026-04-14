package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.BoulderRepository
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymDetailUiState

class FetchBoulderUseCase(
    val repository: BoulderRepository
) {
    suspend operator fun invoke(gymId: String): BoulderUiState {
        val result = repository.fetchBouldersByGymId(gymId)
        if (result.isSuccess) return BoulderUiState.Success(result.getOrThrow())
        return BoulderUiState.Error(message = result.exceptionOrNull()?.message ?: "Failed to fetch boulders")
    }
}