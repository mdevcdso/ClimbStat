package com.example.climbstat.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.model.Topo
import com.example.climbstat.domain.usecase.FetchBoulderToposUseCase
import com.example.climbstat.domain.usecase.FetchBoulderUseCase
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ToposUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoulderDetailsViewModel(
    val fetchBoulderUseCase: FetchBoulderUseCase,
    val fetchToposUseCase: FetchBoulderToposUseCase,

    ): ViewModel() {

    private val _boulderUiState = MutableStateFlow<BoulderUiState>(BoulderUiState.Loading)
    val boulderUiState: StateFlow<BoulderUiState> = _boulderUiState.asStateFlow()

    private val _topoUiState = MutableStateFlow<ToposUiState>(ToposUiState.Loading)
    val topoUiState: StateFlow<ToposUiState> = _topoUiState.asStateFlow()

    private val _selectedGymId = MutableStateFlow<String?>(null)

    fun fetchBoulders(id: String){
        _boulderUiState.value = BoulderUiState.Loading
        viewModelScope.launch {
            val result = fetchBoulderUseCase(id)
            _boulderUiState.value = result
        }
    }

    fun fetchTopos(boulderId: String){
        _topoUiState.value = ToposUiState.Loading
        viewModelScope.launch {
            val result = fetchToposUseCase(boulderId)
            _topoUiState.value = result
        }
    }



}