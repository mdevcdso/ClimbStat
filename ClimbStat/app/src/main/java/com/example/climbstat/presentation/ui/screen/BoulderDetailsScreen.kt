package com.example.climbstat.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.presentation.ui.components.OnErrorComponent
import com.example.climbstat.presentation.ui.components.boulderComponents.BoulderDetailComponent
import com.example.climbstat.presentation.viewModel.BoulderDetailsViewModel

@Composable
fun BoulderDetailsScreen(
    viewModel: BoulderDetailsViewModel,
    navController: NavController,
    gymId: String,
    boulderIndex: Int
) {
    val boulderUiState = viewModel.boulderUiState.collectAsState()
    val topoUiState = viewModel.topoUiState.collectAsState()
    val isUserTopBoulder = viewModel.isUserTopBoulder.collectAsState()

    var actualBoulder = boulderIndex + 1
    val pagerState = rememberPagerState(
        initialPage = boulderIndex,
        pageCount = { actualBoulder }
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
            actualBoulder = boulders.count()

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    viewModel.fetchTopos(boulders[page].id)
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                key = { boulders[it].id },
            ) { page ->
                val boulder = remember(boulders, page) { boulders[page] }
                BoulderDetailComponent(
                    toposUiState = topoUiState.value,
                    boulder = boulder,
                    isUserTopBoulder = isUserTopBoulder.value,
                    onExitClick = {
                        navController.popBackStack()
                    },
                    onTopClick = { nbAttemptString, comment ->
                        val nbAttempt = nbAttemptString.toIntOrNull() ?: 0

                        viewModel.addTopo(boulder.id, nbAttempts = nbAttempt, comment = comment)
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