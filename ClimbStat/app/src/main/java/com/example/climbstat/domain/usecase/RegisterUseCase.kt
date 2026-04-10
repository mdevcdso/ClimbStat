package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.AuthRepository
import com.example.climbstat.domain.usecase.state.AuthStateUi

class RegisterUseCase (
    val repository: AuthRepository
) {
    suspend operator fun invoke(nom: String, email: String, password: String): AuthStateUi{
        val result = repository.register(nom, email, password)
        if (result.isSuccess) return AuthStateUi.Success(result.getOrThrow())
        return AuthStateUi.Error(message = result.exceptionOrNull()?.message ?: "Register failed")
    }
}