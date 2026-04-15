package com.example.climbstat.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.climbstat.presentation.viewModel.BoulderDetailsViewModel
import com.example.climbstat.presentation.viewModel.BoulderViewModel

@Composable
fun BoulderDetailsScreen(
    viewModel: BoulderDetailsViewModel,
    navController: NavController,
    gymId: String
) {
    val boulderUiState = viewModel.boulderUiState.collectAsState()
}