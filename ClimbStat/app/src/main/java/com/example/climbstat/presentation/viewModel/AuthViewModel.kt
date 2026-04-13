package com.example.climbstat.presentation.viewModel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climbstat.domain.usecase.LoginUseCase
import com.example.climbstat.domain.usecase.RegisterUseCase
import com.example.climbstat.domain.usecase.state.AuthUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    var loginUseCase: LoginUseCase,
    var registerUseCase: RegisterUseCase

): ViewModel(){

    private val _authStateUi = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val authStateUi: StateFlow<AuthUiState> = _authStateUi.asStateFlow()

    fun login(email: String, password: String) {
        _authStateUi.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = loginUseCase(email, password)
            Log.e("TestAuth", "Login result: $result")
            _authStateUi.value = result
        }
    }

     fun register(nom: String, email: String, password: String) {
        _authStateUi.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = registerUseCase(nom, email, password)
            _authStateUi.value = result
        }
    }

    fun resetUiState() {
        _authStateUi.value = AuthUiState.Initial
    }
}