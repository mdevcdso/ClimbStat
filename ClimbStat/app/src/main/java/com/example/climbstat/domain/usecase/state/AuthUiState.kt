package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.User

sealed class AuthUiState() {
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String): AuthUiState()
    object Loading: AuthUiState()
    object Initial: AuthUiState()
}