package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.domain.usecase.state.ClimbingGymDetailUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState

class FetchClimbingGymByIdUseCase(
    val repository: ClimbingGymRepository
){
    suspend operator fun invoke(id: String): ClimbingGymDetailUiState {
        val result = repository.fetchClimbingGymById(id)
        if (result.isSuccess) return ClimbingGymDetailUiState.Success(result.getOrThrow())
        return ClimbingGymDetailUiState.Error(message = result.exceptionOrNull()?.message ?: "Failed to fetch climbing gyms")
    }
}