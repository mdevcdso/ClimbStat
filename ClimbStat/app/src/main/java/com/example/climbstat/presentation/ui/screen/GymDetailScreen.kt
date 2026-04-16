package com.example.climbstat.presentation.ui.screen

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.climbstat.R
import com.example.climbstat.domain.usecase.state.ClimbingGymDetailUiState
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState
import com.example.climbstat.presentation.ui.components.ClimbingGymComponents.ClimbingGymInfoCardComponent
import com.example.climbstat.presentation.ui.components.DescriptionComponent
import com.example.climbstat.presentation.ui.components.OnErrorComponent
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.viewModel.ClimbingGymDetailViewModel
import com.example.climbstat.presentation.viewModel.ClimbingGymViewModel
import com.example.climbstat.utils.PointerInputUtils

@Composable
fun GymDetailScreen(
    viewModel: ClimbingGymDetailViewModel,
    navController: NavController,
    gymId: String
) {
    val context = LocalContext.current
    val climbingGymDetailStateUi = viewModel.climbingGymDetailUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchClimbingGymInfo(gymId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(climbingGymDetailStateUi.value) {
            is ClimbingGymDetailUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
            is ClimbingGymDetailUiState.Success -> {
                val gymInfo = (climbingGymDetailStateUi.value as ClimbingGymDetailUiState.Success).gym
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.backward_svgrepo_com),
                            contentDescription = "Icon Button",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        modifier = Modifier
                            .weight(5f)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = gymInfo.name,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.stars_1_svgrepo_com),
                            contentDescription = "Icon Button",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = gymInfo.image,
                        contentDescription = "Image de la salle d'escalade ${gymInfo.name}",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.crags_svgrepo_com),
                        error = painterResource(R.drawable.crags_svgrepo_com),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Column(
                        modifier = Modifier
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f),
                                text = gymInfo.name,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f),
                                text = gymInfo.franchise,
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.End
                            )
                        }
                        ClimbingGymInfoCardComponent(gymInfo)

                        DescriptionComponent(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            description = gymInfo.description,
                            tags = gymInfo.tags
                        )

                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            onClick = {
                                navController.navigate(Screen.Boulder.createRoute(gymInfo.id))
                            }
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.picture_frame_svgrepo_com),
                                    contentDescription = "Icon Button",
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = "Voir les blocs de la salle",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            is ClimbingGymDetailUiState.Error -> {
                val message = (climbingGymDetailStateUi.value as ClimbingGymsUiState.Error).message
                OnErrorComponent(
                    message = "Une erreur est survenue lors du chargement des infos de salle d'escalade.",
                    errorMessage = message,
                    onRetry = {
                        viewModel.fetchClimbingGymInfo(gymId)
                    }
                )
            }
        }
    }
}