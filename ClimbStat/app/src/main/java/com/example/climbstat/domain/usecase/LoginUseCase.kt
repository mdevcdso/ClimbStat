package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.AuthRepository
import com.example.climbstat.domain.usecase.state.AuthUiState

class LoginUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthUiState{
        val result = repository.login(email, password)
        if (result.isSuccess) return AuthUiState.Success(result.getOrThrow())
        return AuthUiState.Error(message = result.exceptionOrNull()?.message ?: "Login failed")
    }
}