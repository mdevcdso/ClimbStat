package com.example.climbstat.presentation.ui.components.ClimbingGymComponents

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.climbstat.R
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.presentation.ui.components.OnEmptySelectionComponent

@Composable
fun ClimbingGymListComponent(
    modifier: Modifier,
    climbingGyms: List<ClimbingGym>,
    onGymClick: (String) -> Unit,
    resetEmptySelection: () -> Unit
) {
    if(climbingGyms.isEmpty()) {
        OnEmptySelectionComponent(
            message = "Aucune salle d'escalade trouvée",
            resetSelection = {
                resetEmptySelection()
            }
        )
    }else {
        LazyColumn(
            modifier = modifier
        ) {
            items(climbingGyms.size) { index ->
                val climbingGym = climbingGyms[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    onClick = {
                        Log.e("ClimbingGymDetailViewModel", climbingGym.name)
                        onGymClick(climbingGym.id)
                    }
                ) {
                    Column {
                        AsyncImage(
                            model = climbingGym.image,
                            contentDescription = "Image de la salle d'escalade ${climbingGym.name}",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.crags_svgrepo_com),
                            error = painterResource(R.drawable.crags_svgrepo_com),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = climbingGym.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = climbingGym.location,
                                    fontSize = 16.sp,
                                    lineHeight = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                    //.weight(1f)
                                )
                                Text(
                                    text = climbingGym.openingHours,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                    //.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}