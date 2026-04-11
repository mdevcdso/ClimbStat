package com.example.climbstat.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.climbstat.presentation.ui.screen.BoulderScreen
import com.example.climbstat.presentation.ui.screen.GymScreen
import com.example.climbstat.presentation.ui.screen.HomeScreen
import com.example.climbstat.presentation.ui.screen.LoginScreen
import com.example.climbstat.presentation.ui.screen.ProfileScreen
import com.example.climbstat.presentation.ui.screen.TopoScreen
import com.example.climbstat.presentation.viewModel.AppViewModels
import com.example.climbstat.presentation.viewModel.AuthViewModel


@Composable
fun AppNavigation(
    viewModels: AppViewModels,
    startDestination: String = Screen.Login.route,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = viewModels.authViewModel, navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }
        composable(route = Screen.Gym.route) {
            GymScreen()
        }
        composable(route = Screen.Boulder.route) {
            BoulderScreen()
        }
        composable(route = Screen.Topo.route) {
            TopoScreen()
        }
    }
}