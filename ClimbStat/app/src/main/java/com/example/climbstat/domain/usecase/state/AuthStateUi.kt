package com.example.climbstat.domain.usecase.state

import com.example.climbstat.domain.model.User

sealed class AuthStateUi() {
    data class Success(val user: User) : AuthStateUi()
    data class Error(val message: String): AuthStateUi()
    object Loading: AuthStateUi()
    object Initial: AuthStateUi()
}