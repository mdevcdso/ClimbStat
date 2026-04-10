package com.example.climbstat.presentation.viewModel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.climbstat.domain.usecase.LoginUseCase
import com.example.climbstat.domain.usecase.RegisterUseCase
import com.example.climbstat.domain.usecase.state.AuthStateUi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    var loginUseCase: LoginUseCase,
    var registerUseCase: RegisterUseCase

): ViewModel(){

    private val _authStateUi = MutableStateFlow<AuthStateUi>(AuthStateUi.Initial)
    val authStateUi: StateFlow<AuthStateUi> = _authStateUi.asStateFlow()

    fun login(email: String, password: String) {
        _authStateUi.value = AuthStateUi.Loading
        viewModelScope.launch {
            val result = loginUseCase(email, password)
            Log.e("TestAuth", "Login result: $result")
            _authStateUi.value = result
        }
    }

     fun register(nom: String, email: String, password: String) {
        _authStateUi.value = AuthStateUi.Loading
        viewModelScope.launch {
            val result = registerUseCase(nom, email, password)
            _authStateUi.value = result
        }
    }

    fun resetUiState() {
        _authStateUi.value = AuthStateUi.Initial
    }
}