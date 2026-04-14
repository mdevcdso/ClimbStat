package com.example.climbstat.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.usecase.FetchClimbingGymByIdUseCase
import com.example.climbstat.domain.usecase.FetchClimbingGymUseCase
import com.example.climbstat.domain.usecase.state.ClimbingGymDetailUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClimbingGymDetailViewModel(
    var fetchClimbingGymByIdUseCase: FetchClimbingGymByIdUseCase
): ViewModel() {

    private val _climbingGymDetailUiState = MutableStateFlow<ClimbingGymDetailUiState>(ClimbingGymDetailUiState.Loading)
    val climbingGymDetailUiState: StateFlow<ClimbingGymDetailUiState> = _climbingGymDetailUiState.asStateFlow()

    fun fetchClimbingGymInfo(id: String){
        _climbingGymDetailUiState.value = ClimbingGymDetailUiState.Loading
        viewModelScope.launch {
            val result = fetchClimbingGymByIdUseCase(id);
            Log.d("ClimbingGymDetailViewModel", "fetchClimbingGymInfo: result = $result")
            _climbingGymDetailUiState.value = result
        }
    }
}