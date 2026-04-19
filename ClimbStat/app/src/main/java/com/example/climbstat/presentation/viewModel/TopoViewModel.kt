package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.model.DifficultyTier
import com.example.climbstat.domain.model.GymStats
import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.domain.repository.TopoRepository
import com.example.climbstat.domain.usecase.state.GymStatsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TopoViewModel(
    private val topoRepository: TopoRepository,
    private val climbingGymRepository: ClimbingGymRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<GymStatsUiState>(GymStatsUiState.Loading)
    val uiState: StateFlow<GymStatsUiState> = _uiState.asStateFlow()

    fun loadStats() {
        _uiState.value = GymStatsUiState.Loading
        viewModelScope.launch {
            val toposResult = topoRepository.getUserTopos()
            if (toposResult.isFailure) {
                _uiState.value = GymStatsUiState.Error(
                    toposResult.exceptionOrNull()?.message ?: "Erreur de chargement"
                )
                return@launch
            }
            val topos = toposResult.getOrThrow()
            val toposByGym = topos
                .filter { it.boulderGymId != null }
                .groupBy { it.boulderGymId!! }

            if (toposByGym.isEmpty()) {
                _uiState.value = GymStatsUiState.Empty
                return@launch
            }

            val gymsById = climbingGymRepository.fetchClimbingGyms()
                .getOrNull()
                .orEmpty()
                .associateBy { it.id }

            val stats = toposByGym.mapNotNull { (gymId, gymTopos) ->
                val gym = gymsById[gymId] ?: return@mapNotNull null
                GymStats(
                    gymId = gym.id,
                    gymName = gym.name,
                    totalTops = gymTopos.size,
                    flashRate = gymTopos.count { it.isFlash }.toFloat() / gymTopos.size,
                    topsByTier = DifficultyTier.values().associateWith { tier ->
                        gymTopos.count { DifficultyTier.fromColor(it.boulderDifficulty) == tier }
                    }
                )
            }.sortedByDescending { it.totalTops }

            _uiState.value = if (stats.isEmpty()) {
                GymStatsUiState.Empty
            } else {
                GymStatsUiState.Success(stats)
            }
        }
    }
}
