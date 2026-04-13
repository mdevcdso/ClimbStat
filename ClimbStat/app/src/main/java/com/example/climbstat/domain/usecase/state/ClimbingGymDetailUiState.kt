package com.example.climbstat.domain.usecase.state;

import com.example.climbstat.domain.model.ClimbingGym

sealed class ClimbingGymDetailUiState {
    data class Success(val gym: ClimbingGym) : ClimbingGymDetailUiState()
    data class Error(val message: String) : ClimbingGymDetailUiState()
    object Loading : ClimbingGymDetailUiState()
}