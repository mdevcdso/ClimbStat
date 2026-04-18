package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.model.DifficultyTier
import com.example.climbstat.domain.repository.TopoRepository
import com.example.climbstat.domain.usecase.state.ProfileUiState
import com.example.climbstat.utils.TokenManagerUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val tokenManager: TokenManagerUtils,
    private val topoRepository: TopoRepository
) : ViewModel() {
    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    fun loadProfile() {
        val userName = tokenManager.getUserName()
        if (userName.isNullOrBlank()) {
            _profileUiState.value = ProfileUiState.Error("Utilisateur non connecté")
            return
        }
        _profileUiState.value = ProfileUiState.Loading
        viewModelScope.launch {
            val result = topoRepository.getUserTopos()
            if (result.isFailure) {
                _profileUiState.value = ProfileUiState.Error(
                    result.exceptionOrNull()?.message ?: "Erreur de chargement"
                )
                return@launch
            }
            val topos = result.getOrThrow()
            val sessionsCount = topos
                .map { it.attemptDate.substringBefore('T') }
                .toSet()
                .size
            val topsByTier = DifficultyTier.values().associateWith { tier ->
                topos.count { DifficultyTier.fromColor(it.boulderDifficulty) == tier }
            }
            _profileUiState.value = ProfileUiState.Success(
                userName = userName,
                sessionsCount = sessionsCount,
                topsByTier = topsByTier
            )
        }
    }

    fun logout() {
        tokenManager.clearToken()
    }
}
