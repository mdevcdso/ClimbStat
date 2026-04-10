package com.example.climbstat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.climbstat.data.datasource.remote.RemoteUserDataSource
import com.example.climbstat.data.remote.ClimbStatApiAuthService
import com.example.climbstat.data.remote.ClimbStatApiClient
import com.example.climbstat.data.repository.AuthRepositoryImpl
import com.example.climbstat.domain.usecase.LoginUseCase
import com.example.climbstat.domain.usecase.RegisterUseCase
import com.example.climbstat.domain.usecase.state.AuthStateUi
import com.example.climbstat.presentation.ui.navigation.AppNavigation
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.ui.screen.LoginScreen
import com.example.climbstat.presentation.ui.theme.ClimbStatTheme
import com.example.climbstat.presentation.viewModel.AppViewModels
import com.example.climbstat.presentation.viewModel.AuthViewModel
import com.example.climbstat.utils.TokenManagerUtils

class MainActivity : ComponentActivity() {

    lateinit var loginUseCase: LoginUseCase
    lateinit var registerUseCase: RegisterUseCase
    lateinit var authViewModel: AuthViewModel

    private lateinit var appViewModels: AppViewModels


    override fun onCreate(savedInstanceState: Bundle?) {
        val tokenManager = TokenManagerUtils(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appViewModels = injectDependencies(tokenManager)


        setContent {
            ClimbStatTheme {
                val start = if (tokenManager.hasToken()) Screen.Home.route else Screen.Login.route
                MainContent(appViewModels, start)
            }
        }
    }

    private fun injectDependencies(tokenManager: TokenManagerUtils): AppViewModels {
        val authRepository = AuthRepositoryImpl(
            remote = RemoteUserDataSource(
                ClimbStatApiClient.authApiService
            ),
            tokenManager = tokenManager
        )
        loginUseCase = LoginUseCase(authRepository)
        registerUseCase = RegisterUseCase(authRepository)
        authViewModel = AuthViewModel(loginUseCase, registerUseCase)

        return AppViewModels(
            authViewModel = AuthViewModel(loginUseCase, registerUseCase)
        )
    }
}

@Composable
fun MainContent(viewModels: AppViewModels, startDestination: String) {
    ClimbStatTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFFFFFFF),
                            Color(0xFF00E3FF),
                        )
                    )
                ),
            color = Color.Transparent
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                AppNavigation(viewModels, startDestination)
            }
        }
    }
}