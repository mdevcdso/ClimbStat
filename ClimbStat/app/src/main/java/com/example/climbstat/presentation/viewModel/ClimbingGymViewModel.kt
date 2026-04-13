package com.example.climbstat.presentation.viewModel

import android.util.Log
import androidx.compose.animation.core.AnimationState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.usecase.FetchClimbingGymUseCase
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClimbingGymViewModel(
    var getClimbingGymUseCase: FetchClimbingGymUseCase
): ViewModel() {
    private val _climbingGymUiState = MutableStateFlow<ClimbingGymsUiState>(ClimbingGymsUiState.Loading)
    val climbingGymUiState: StateFlow<ClimbingGymsUiState> = _climbingGymUiState.asStateFlow()

    fun fetchClimbingGyms(){
        _climbingGymUiState.value = ClimbingGymsUiState.Loading
        viewModelScope.launch {
            val result = getClimbingGymUseCase();
            Log.e("TestClimbingGym", "$result")
            _climbingGymUiState.value = result
        }
    }
}