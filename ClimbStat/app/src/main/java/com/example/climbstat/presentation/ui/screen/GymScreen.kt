package com.example.climbstat.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.climbstat.R
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import com.example.climbstat.presentation.ui.components.ClimbingGymListComponent
import com.example.climbstat.presentation.ui.components.HeaderComponent
import com.example.climbstat.presentation.ui.components.OnErrorComponent
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.viewModel.AppViewModel
import com.example.climbstat.presentation.viewModel.ClimbingGymViewModel
import com.example.climbstat.utils.PointerInputUtils

@Composable
fun GymScreen(
    viewModel: ClimbingGymViewModel,
    navController: NavController
) {
    val pointerUtils = PointerInputUtils()
    val context = LocalContext.current
    val climbingGymStateUi = viewModel.climbingGymUiState.collectAsState()

    var refreshScrollState = remember { mutableStateOf(false) }
    var searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if(climbingGymStateUi.value is ClimbingGymsUiState.Success) return@LaunchedEffect
        viewModel.fetchClimbingGyms()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .run {
                with(pointerUtils) {
                    verticalDragToRefresh(
                        refreshScrollState = refreshScrollState
                    ) {
                        viewModel.fetchClimbingGyms()
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HeaderComponent(
            title = "Salles d'escalade",
            iconScreen = R.drawable.buildings_svgrepo_com,
            iconButton = null,
            modifier = Modifier.fillMaxWidth(),
            onclick = {}
        )
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text("Rechercher une salle...") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "Recherche",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            singleLine = true
        )
        when(climbingGymStateUi.value){
            is ClimbingGymsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
            is ClimbingGymsUiState.Success -> {
                val gyms = (climbingGymStateUi.value as ClimbingGymsUiState.Success).climbingGyms
                if(gyms.isEmpty()){
                    ClimbingGymListComponent(
                        message = "Malheuresement, aucune salle d'escalade trouvée"
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        ClimbingGymListComponent(
                            modifier = Modifier.fillMaxSize(),
                            climbingGyms = gyms.filter { it.name.contains(searchQuery.value, ignoreCase = true) },
                            onGymClick = { gymId ->
                                navController.navigate(Screen.GymDetail.createRoute(gymId))
                            },
                            resetEmptySelection = {
                                searchQuery.value = ""
                            }
                        )
                        if (refreshScrollState.value) {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(50)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(16.dp)

                                )
                            }
                        }
                    }
                }
            }
             is ClimbingGymsUiState.Error -> {
                val message = (climbingGymStateUi.value as ClimbingGymsUiState.Error).message
                 OnErrorComponent(
                    message = "Une erreur est survenue lors du chargement des salles d'escalade.",
                    errorMessage = message,
                    onRetry = {
                        viewModel.fetchClimbingGyms()
                    }
                 )
             }
        }
    }
}