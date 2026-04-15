package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.usecase.FetchBoulderUseCase
import com.example.climbstat.domain.usecase.FetchClimbingGymUseCase
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoulderViewModel(
    val fetchBoulderUseCase: FetchBoulderUseCase,
    val fetchClimbingGymUseCase: FetchClimbingGymUseCase
): ViewModel(){

    private val _boulderUiState = MutableStateFlow<BoulderUiState>(BoulderUiState.Loading)
    val boulderUiState: StateFlow<BoulderUiState> = _boulderUiState.asStateFlow()

    private val _climbingGymUiState = MutableStateFlow<ClimbingGymsUiState>(ClimbingGymsUiState.Loading)
    val climbingGymsUiState: StateFlow<ClimbingGymsUiState> = _climbingGymUiState.asStateFlow()

    private val _selectedGym = MutableStateFlow<ClimbingGym?>(null)
    val selectedGym: StateFlow<ClimbingGym?> = _selectedGym.asStateFlow()


     fun fetchBoulders(id: String){
        _boulderUiState.value = BoulderUiState.Loading
         viewModelScope.launch {
            val result = fetchBoulderUseCase(id)
            _boulderUiState.value = result
         }
    }

    fun fetchClimbingGymInfo(gymId: String? = null){
        _boulderUiState.value = BoulderUiState.Loading
        viewModelScope.launch {

            val gymResult = fetchClimbingGymUseCase()
            _climbingGymUiState.value = gymResult

            if(gymResult is ClimbingGymsUiState.Success){
                if(gymId != null){
                    _selectedGym.value = gymResult.climbingGyms.firstOrNull { it.id == gymId }
                    fetchBoulders(gymId)
                    return@launch
                }
                val climbingGym = gymResult.climbingGyms.firstOrNull()
                _selectedGym.value = climbingGym
                if(climbingGym != null){
                    fetchBoulders(climbingGym.id)
                } else {
                    _boulderUiState.value = BoulderUiState.Error("Climbing gym not found")
                }
            }else {
                _boulderUiState.value = BoulderUiState.Error("Failed to fetch climbing gyms")
            }
        }
    }
}