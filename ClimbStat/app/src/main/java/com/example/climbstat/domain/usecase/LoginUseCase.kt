package com.example.climbstat.domain.usecase

import com.example.climbstat.domain.repository.AuthRepository
import com.example.climbstat.domain.usecase.state.AuthStateUi

class LoginUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthStateUi{
        val result = repository.login(email, password)
        if (result.isSuccess) return AuthStateUi.Success(result.getOrThrow())
        return AuthStateUi.Error(message = result.exceptionOrNull()?.message ?: "Login failed")
    }
}