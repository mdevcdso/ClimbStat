package com.example.climbstat.presentation.ui.screen

import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.climbstat.domain.usecase.state.ClimbingGymDetailUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import com.example.climbstat.presentation.viewModel.ClimbingGymDetailViewModel
import com.example.climbstat.presentation.viewModel.ClimbingGymViewModel
import com.example.climbstat.utils.PointerInputUtils

@Composable
fun GymDetailScreen(
    viewModel: ClimbingGymDetailViewModel,
    navController: NavController,
    gymId: String
) {
    val pointerUtils = PointerInputUtils()
    val context = LocalContext.current
    val climbingGymDetailStateUi = viewModel.climbingGymDetailUiState.collectAsState()

    var refreshScrollState = remember { mutableStateOf(false) }
    var searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if(climbingGymDetailStateUi.value is ClimbingGymDetailUiState.Success) return@LaunchedEffect
        viewModel.fetchClimbingGymInfo("1")
    }

    Text("Gym Detail Screen - ID: $gymId")
}