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
import com.example.climbstat.data.datasource.remote.RemoteBoulderDataSource
import com.example.climbstat.data.datasource.remote.RemoteClimbingGymDataSource
import com.example.climbstat.data.datasource.remote.RemoteUserDataSource
import com.example.climbstat.data.remote.ClimbStatApiClient
import com.example.climbstat.data.repository.AuthRepositoryImpl
import com.example.climbstat.data.repository.BoulderRepositoryImpl
import com.example.climbstat.data.repository.ClimbingGymRepositoryImpl
import com.example.climbstat.domain.repository.BoulderRepository
import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.domain.usecase.AddNewTopoUseCase
import com.example.climbstat.domain.usecase.FetchBoulderToposUseCase
import com.example.climbstat.domain.usecase.FetchBoulderUseCase
import com.example.climbstat.domain.usecase.FetchClimbingGymByIdUseCase
import com.example.climbstat.domain.usecase.FetchClimbingGymUseCase
import com.example.climbstat.domain.usecase.LoginUseCase
import com.example.climbstat.domain.usecase.RegisterUseCase
import com.example.climbstat.presentation.ui.navigation.AppNavigation
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.ui.navigation.composable.BottomNavigationBar
import com.example.climbstat.presentation.ui.theme.ClimbStatTheme
import com.example.climbstat.presentation.viewModel.AppViewModel
import com.example.climbstat.presentation.viewModel.AuthViewModel
import com.example.climbstat.presentation.viewModel.BoulderDetailsViewModel
import com.example.climbstat.presentation.viewModel.BoulderViewModel
import com.example.climbstat.presentation.viewModel.ClimbingGymDetailViewModel
import com.example.climbstat.presentation.viewModel.ClimbingGymViewModel
import com.example.climbstat.presentation.viewModel.ProfileViewModel
import com.example.climbstat.utils.TokenManagerUtils

class MainActivity : ComponentActivity() {

    lateinit var loginUseCase: LoginUseCase
    lateinit var registerUseCase: RegisterUseCase
    lateinit var fetchClimbingGymsUseCase: FetchClimbingGymUseCase
    lateinit var fetchClimbingGymsByIdUseCase: FetchClimbingGymByIdUseCase
    lateinit var fetchBoulderUseCase: FetchBoulderUseCase
    lateinit var fetchBoulderToposUseCase: FetchBoulderToposUseCase
    lateinit var addNewTopoUseCase: AddNewTopoUseCase

    private lateinit var appViewModels: AppViewModel

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

    private fun injectDependencies(tokenManager: TokenManagerUtils): AppViewModel {
        val authRepository = AuthRepositoryImpl(
            remote = RemoteUserDataSource(
                ClimbStatApiClient.authApiService
            ),
            tokenManager = tokenManager
        )
        val climbingGymRepository = ClimbingGymRepositoryImpl(
                remote = RemoteClimbingGymDataSource(
                    ClimbStatApiClient.gymApiService
                ),
                tokenManager = tokenManager
        )
        val boulderRepository = BoulderRepositoryImpl(
            remote = RemoteBoulderDataSource(
                ClimbStatApiClient.boulderApiService
            ),
            tokenManager = tokenManager
        )
        val topoRepository = com.example.climbstat.data.repository.TopoRepositoryImpl(
            remote = com.example.climbstat.data.datasource.remote.RemoteTopoDataSource(
                ClimbStatApiClient.topoApiService
            ),
            tokenManager = tokenManager
        )
        loginUseCase = LoginUseCase(authRepository)
        registerUseCase = RegisterUseCase(authRepository)
        fetchClimbingGymsUseCase = FetchClimbingGymUseCase(climbingGymRepository)
        fetchClimbingGymsByIdUseCase = FetchClimbingGymByIdUseCase(climbingGymRepository)
        fetchBoulderUseCase = FetchBoulderUseCase(boulderRepository)
        fetchBoulderToposUseCase = FetchBoulderToposUseCase(topoRepository)
        addNewTopoUseCase = AddNewTopoUseCase(topoRepository)

        return AppViewModel(
            authViewModel = AuthViewModel(loginUseCase, registerUseCase),
            climbingGymsViewModel = ClimbingGymViewModel(fetchClimbingGymsUseCase),
            climbingGymDetailViewModel = ClimbingGymDetailViewModel(fetchClimbingGymsByIdUseCase),
            boulderViewModel = BoulderViewModel(fetchBoulderUseCase, fetchClimbingGymsUseCase),
            boulderDetailsViewModel = BoulderDetailsViewModel(fetchBoulderUseCase, fetchBoulderToposUseCase, addNewTopoUseCase, tokenManager.getUserId() ?: ""),
            profileViewModel = ProfileViewModel(tokenManager, topoRepository)
        )
    }
}

@Composable
fun MainContent(viewModels: AppViewModel, startDestination: String) {
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