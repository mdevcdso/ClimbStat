package com.example.climbstat.data.remote.auth

import com.example.climbstat.domain.model.User
import com.google.gson.annotations.SerializedName

data class AuthDto (
    @SerializedName("access_token")
    val accessToken: String,
    val user: User
)

fun AuthDto.toDomainModel(): User = User(
    token = this.accessToken,
    id = this.user.id,
    email = this.user.email,
    name = this.user.name,
    role = this.user.role
)