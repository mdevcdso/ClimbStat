package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.climbstat.domain.usecase.state.ProfileUiState
import com.example.climbstat.utils.TokenManagerUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(private val tokenManager: TokenManagerUtils) : ViewModel() {
    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    fun loadProfile() {
        val userName = tokenManager.getUserName()
        if (userName.isNullOrBlank()) {
            _profileUiState.value = ProfileUiState.Error("Utilisateur non connecté")
            return
        }
        _profileUiState.value = ProfileUiState.Success(userName = userName)
    }

    fun logout() {
        tokenManager.clearToken()
    }
}