package com.example.climbstat.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.data.remote.topo.AddTopoRequest
import com.example.climbstat.domain.model.Topo
import com.example.climbstat.domain.usecase.AddNewTopoUseCase
import com.example.climbstat.domain.usecase.FetchBoulderToposUseCase
import com.example.climbstat.domain.usecase.FetchBoulderUseCase
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ToposUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Comment

class BoulderDetailsViewModel(
    val fetchBoulderUseCase: FetchBoulderUseCase,
    val fetchToposUseCase: FetchBoulderToposUseCase,
    val addTopoUseCase: AddNewTopoUseCase,
    val userId: String
): ViewModel() {

    private val _boulderUiState = MutableStateFlow<BoulderUiState>(BoulderUiState.Loading)
    val boulderUiState: StateFlow<BoulderUiState> = _boulderUiState.asStateFlow()

    private val _topoUiState = MutableStateFlow<ToposUiState>(ToposUiState.Loading)
    val topoUiState: StateFlow<ToposUiState> = _topoUiState.asStateFlow()

    private val _isUserTopBoulder = MutableStateFlow<Boolean>(false)
    val isUserTopBoulder: StateFlow<Boolean> = _isUserTopBoulder.asStateFlow()

    fun fetchBoulders(id: String){
        _boulderUiState.value = BoulderUiState.Loading
        viewModelScope.launch {
            val result = fetchBoulderUseCase(id)
            _boulderUiState.value = result
        }
    }

    fun fetchTopos(boulderId: String){
        _topoUiState.value = ToposUiState.Loading
        _isUserTopBoulder.value = false
        viewModelScope.launch {
            val result = fetchToposUseCase(boulderId)
            _topoUiState.value = result
            if(result is ToposUiState.Success){
                _isUserTopBoulder.value = result.topos.any { it.userId == userId }
            }
        }
    }

    fun addTopo(boulderId: String, nbAttempts: Int, comment: String){
        viewModelScope.launch {
            var isFlash = true
            if(nbAttempts > 1) isFlash = false
            val addTopoRequest = AddTopoRequest(
                isFlash = isFlash,
                nbAttempts = nbAttempts,
                comment = comment
            )
            val result = addTopoUseCase(boulderId, addTopoRequest)
            if(result != null){
                fetchTopos(boulderId)
            }
        }
    }



}