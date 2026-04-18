package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.model.DifficultyTier

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(
        val userName: String,
        val sessionsCount: Int,
        val topsByTier: Map<DifficultyTier, Int>,
        val visitedGyms: List<ClimbingGym>
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
