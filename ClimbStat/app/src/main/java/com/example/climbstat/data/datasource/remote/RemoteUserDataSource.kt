package com.example.climbstat.data.datasource.remote

import com.example.climbstat.data.remote.ClimbStatApiAuthService
import com.example.climbstat.data.remote.auth.AuthDto
import com.example.climbstat.data.remote.auth.LoginRequest
import com.example.climbstat.data.remote.auth.RegisterRequest
import com.example.climbstat.data.remote.auth.toDomainModel
import com.example.climbstat.domain.model.User

class RemoteUserDataSource(
    private val apiService: ClimbStatApiAuthService
){
    suspend fun register(name: String, email: String, password: String): Result<User> {
        return try {
            val requestBody = RegisterRequest(
                name = name,
                email = email,
                password = password
            )
            val response = this.apiService.userRegister(
                request = requestBody
            )
            val user = response.toDomainModel()
            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val loginBody = LoginRequest(
                email = email,
                password = password
            )
            val response = this.apiService.userLogin(
                request = loginBody
            )
            val user = response.toDomainModel()
            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}