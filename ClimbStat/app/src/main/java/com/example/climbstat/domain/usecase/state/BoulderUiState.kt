package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.Boulder

sealed class BoulderUiState {
    data class Success(val boulders: List<Boulder>) : BoulderUiState()
    data class Error(val message: String): BoulderUiState()
    data object Loading: BoulderUiState()
}