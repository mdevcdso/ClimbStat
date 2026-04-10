package com.example.climbstat.domain.repository

import com.example.climbstat.data.datasource.remote.RemoteUserDataSource
import com.example.climbstat.domain.model.User

interface AuthRepository{
    suspend fun login(email: String, password: String): Result<User>

    suspend fun register(name: String, email: String, password: String): Result<User>
}