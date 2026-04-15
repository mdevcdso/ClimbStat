package com.example.climbstat.presentation.ui.components.boulderComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.usecase.state.ClimbingGymsUiState

@Composable
fun ClimbingGymListComponents(
    climbingGymUiState: ClimbingGymsUiState,
    modifier: Modifier,
    onClick: (String) -> Unit
){
    Box(
        modifier = modifier
    ) {
        when (climbingGymUiState) {
            is ClimbingGymsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }

            is ClimbingGymsUiState.Success -> {
                val climbingGyms = climbingGymUiState.climbingGyms
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(climbingGyms.size) { index ->
                        val climbingGym = climbingGyms[index]
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        onClick(climbingGym.id)
                                    }
                                )
                        ) {
                            Text(
                                text = climbingGym.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            is ClimbingGymsUiState.Error -> {
                Text(
                    "Impossible de charger les salles d'escalades",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}