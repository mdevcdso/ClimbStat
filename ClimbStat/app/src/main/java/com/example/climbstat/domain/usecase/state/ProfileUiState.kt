package com.example.climbstat.domain.usecase.state

import android.os.Message

sealed class ProfileUiState {
    data object Loading: ProfileUiState()
    data class Success(val userName: String): ProfileUiState()
    data class Error(val message: String): ProfileUiState()
}