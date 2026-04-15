package com.example.climbstat.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.climbstat.domain.model.Boulder
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import com.example.climbstat.presentation.ui.components.OnErrorComponent
import com.example.climbstat.presentation.ui.components.boulderComponents.BoulderDetailComponent
import com.example.climbstat.presentation.viewModel.BoulderDetailsViewModel
import com.example.climbstat.presentation.viewModel.BoulderViewModel
import kotlin.text.get

@Composable
fun BoulderDetailsScreen(
    viewModel: BoulderDetailsViewModel,
    navController: NavController,
    gymId: String,
    boulderIndex: Int
) {
    val boulderUiState = viewModel.boulderUiState.collectAsState()
    val topoUiState = viewModel.topoUiState.collectAsState()

    var actualMonthNumber = boulderIndex + 1
    val pagerState = rememberPagerState(
        initialPage = boulderIndex,
        pageCount = { actualMonthNumber }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchBoulders(gymId)
    }

    when (boulderUiState.value) {
        is BoulderUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
        is BoulderUiState.Success -> {

            val boulders = (boulderUiState.value as BoulderUiState.Success).boulders
            actualMonthNumber = boulders.count()

            LaunchedEffect(pagerState.currentPage) {
                viewModel.fetchTopos(boulders[pagerState.currentPage].id)
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                var boulder = boulders[page]
                BoulderDetailComponent(
                    toposUiState = topoUiState.value,
                    boulder = boulder,
                    onExitClick = {
                        navController.popBackStack()
                    },
                    onFlashClick = {

                    },
                    onTopClick = {

                    }
                )
            }
        }
        is BoulderUiState.Error -> {
            val message = (boulderUiState.value as BoulderUiState.Error).message
            OnErrorComponent(
                message = "Une erreur est survenue lors du chargement du bloc",
                errorMessage = message,
                onRetry = {
                    viewModel.fetchBoulders(gymId)
                }
            )
        }
    }
}