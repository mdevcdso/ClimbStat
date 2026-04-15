package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.Topo

sealed class ToposUiState {
    data class Success(val topos: List<Topo>) : ToposUiState()
    data class Error(val message: String): ToposUiState()
    data object Loading: ToposUiState()
}