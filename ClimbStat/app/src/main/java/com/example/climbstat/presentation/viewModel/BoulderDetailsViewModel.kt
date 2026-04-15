package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.usecase.FetchBoulderUseCase
import com.example.climbstat.domain.usecase.state.BoulderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoulderDetailsViewModel(
    val fetchBoulderUseCase: FetchBoulderUseCase,
): ViewModel() {

    private val _boulderUiState = MutableStateFlow<BoulderUiState>(BoulderUiState.Loading)
    val boulderUiState: StateFlow<BoulderUiState> = _boulderUiState.asStateFlow()

    fun fetchBoulders(id: String){
        _boulderUiState.value = BoulderUiState.Loading
        viewModelScope.launch {
            val result = fetchBoulderUseCase(id)
            _boulderUiState.value = result
        }
    }

}