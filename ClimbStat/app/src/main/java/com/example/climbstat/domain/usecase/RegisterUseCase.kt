package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.AuthRepository
import com.example.climbstat.domain.usecase.state.AuthUiState

class RegisterUseCase (
    val repository: AuthRepository
) {
    suspend operator fun invoke(nom: String, email: String, password: String): AuthUiState{
        val result = repository.register(nom, email, password)
        if (result.isSuccess) return AuthUiState.Success(result.getOrThrow())
        return AuthUiState.Error(message = result.exceptionOrNull()?.message ?: "Register failed")
    }
}