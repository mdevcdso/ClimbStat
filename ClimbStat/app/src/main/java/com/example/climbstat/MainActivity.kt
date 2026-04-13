package com.example.climbstat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.climbstat.data.datasource.remote.RemoteUserDataSource
import com.example.climbstat.data.remote.ClimbStatApiClient
import com.example.climbstat.data.repository.AuthRepositoryImpl
import com.example.climbstat.domain.usecase.LoginUseCase
import com.example.climbstat.domain.usecase.RegisterUseCase
import com.example.climbstat.presentation.ui.navigation.AppNavigation
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.ui.navigation.composable.BottomNavigationBar
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
        val navController: NavHostController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AppNavigation(viewModels, startDestination, navController)
            }
        }
    }
}