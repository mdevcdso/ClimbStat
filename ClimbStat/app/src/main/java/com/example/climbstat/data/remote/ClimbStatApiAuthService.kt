package com.example.climbstat.data.remote

import com.example.climbstat.data.remote.auth.AuthDto
import com.example.climbstat.data.remote.auth.LoginRequest
import com.example.climbstat.data.remote.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClimbStatApiAuthService {
    @POST("auth/login")
    suspend fun userLogin(
        @Body request: LoginRequest
    ): AuthDto

    @POST("auth/register")
    suspend fun userRegister(
        @Body request: RegisterRequest
    ): AuthDto

}