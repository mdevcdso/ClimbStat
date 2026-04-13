package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState

class FetchClimbingGymUseCase(
    val repository: ClimbingGymRepository
){
    suspend operator fun invoke(): ClimbingGymsUiState {
        val result = repository.fetchClimbingGyms()
        if (result.isSuccess) return ClimbingGymsUiState.Success(result.getOrThrow())
        return ClimbingGymsUiState.Error(message = result.exceptionOrNull()?.message ?: "Failed to fetch climbing gyms")
    }
}