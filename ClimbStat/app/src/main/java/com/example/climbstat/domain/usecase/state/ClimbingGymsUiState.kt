package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.ClimbingGym

sealed class ClimbingGymsUiState() {
    data class Success(val climbingGyms: List<ClimbingGym>) : ClimbingGymsUiState()
    data class Error(val message: String): ClimbingGymsUiState()
    object Loading: ClimbingGymsUiState()
}