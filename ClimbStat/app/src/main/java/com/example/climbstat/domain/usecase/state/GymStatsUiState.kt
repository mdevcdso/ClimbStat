package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.GymStats

sealed class GymStatsUiState {
    data object Loading : GymStatsUiState()
    data object Empty : GymStatsUiState()
    data class Success(val gymStats: List<GymStats>) : GymStatsUiState()
    data class Error(val message: String) : GymStatsUiState()
}
