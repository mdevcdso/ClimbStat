package com.example.climbstat.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.climbstat.R
import com.example.climbstat.domain.usecase.state.BoulderUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import com.example.climbstat.presentation.ui.components.HeaderComponent
import com.example.climbstat.presentation.ui.components.OnErrorComponent
import com.example.climbstat.presentation.ui.components.boulderComponents.BoulderListComponent
import com.example.climbstat.presentation.ui.components.boulderComponents.ClimbingGymListComponents
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.viewModel.BoulderViewModel
import com.example.climbstat.utils.PointerInputUtils

@Composable
fun BoulderScreen(
    viewModel: BoulderViewModel,
    gymId: String?,
    navController: NavController
) {
    val pointerUtils = PointerInputUtils()
    val context = LocalContext.current

    val boulderUiState = viewModel.boulderUiState.collectAsState()
    val climbingGymUiState = viewModel.climbingGymsUiState.collectAsState()

    val selectedGym = viewModel.selectedGym.collectAsState().value

    val refreshScrollState = remember { mutableStateOf(false) }
    val isSelectDown = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if(gymId != null && gymId != "{gymId}") {
            viewModel.fetchClimbingGymInfo(gymId)
        } else {
            viewModel.fetchClimbingGymInfo()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .run {
                with(pointerUtils) {
                    verticalDragToRefresh(
                        refreshScrollState = refreshScrollState
                    ) {
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderComponent(
            title = "Blocs",
            iconScreen = R.drawable.picture_frame_svgrepo_com,
            iconButton = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onclick = {}
        )
        when(boulderUiState.value) {
            is BoulderUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }

            is BoulderUiState.Success -> {
                val boulders = (boulderUiState.value as BoulderUiState.Success).boulders
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                isSelectDown.value = !isSelectDown.value
                            }
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            selectedGym?.name ?: "",
                            modifier = Modifier,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_swap_vert_24),
                            contentDescription = "Refresh",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(8.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    BoulderListComponent(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        boulders = boulders,
                        onBoulderClick = { gymId ->
                            navController.navigate(Screen.BoulderDetail.createRoute(gymId))
                        }
                    )
                    if (isSelectDown.value) {
                        ClimbingGymListComponents(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                                .fillMaxWidth(),
                            climbingGymUiState = climbingGymUiState.value,
                            onClick = { gymId ->
                                viewModel.fetchClimbingGymInfo(gymId)
                                isSelectDown.value = false
                            }
                        )
                    }
                }
            }
            is BoulderUiState.Error -> {
                val message = (boulderUiState.value as BoulderUiState.Error).message
                OnErrorComponent(
                    message = "Une erreur est survenue lors du chargement des blocs pour la salle ${selectedGym?.name ?: ""} : $message",
                    errorMessage = message,
                    onRetry = {
                        viewModel.fetchClimbingGymInfo(selectedGym?.id)
                    }
                )
            }
        }
    }
}