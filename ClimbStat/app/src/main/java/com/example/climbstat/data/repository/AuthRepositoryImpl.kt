package com.example.climbstat.data.repository

import com.example.climbstat.data.datasource.remote.RemoteUserDataSource
import com.example.climbstat.data.remote.auth.RegisterRequest
import com.example.climbstat.domain.model.User
import com.example.climbstat.domain.repository.AuthRepository
import com.example.climbstat.utils.TokenManagerUtils

class AuthRepositoryImpl(
    val remote: RemoteUserDataSource,
    val tokenManager: TokenManagerUtils
): AuthRepository{
    override suspend fun login(email: String, password: String): Result<User> {
        val loginResult = remote.login(email, password)
        return if (loginResult.isSuccess) {
            val user = loginResult.getOrThrow()
            tokenManager.saveToken(user.token)
            Result.success(user)
        } else {
            Result.failure(Exception("Login failed : ${loginResult.exceptionOrNull()?.message}"))
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<User> {
        val registerResult = remote.register(name, email, password)
        return if (registerResult.isSuccess) {
            val user = registerResult.getOrThrow()
            tokenManager.saveToken(user.token)
            Result.success(user)
        } else {
            Result.failure(Exception("Register failed : ${registerResult.exceptionOrNull()?.message}"))
        }
    }
}