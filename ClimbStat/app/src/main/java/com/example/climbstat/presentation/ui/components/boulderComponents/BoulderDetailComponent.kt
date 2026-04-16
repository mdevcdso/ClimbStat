package com.example.climbstat.presentation.ui.components.boulderComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.climbstat.domain.model.Boulder
import com.example.climbstat.domain.usecase.state.ToposUiState
import com.example.climbstat.presentation.ui.components.DescriptionComponent
import com.example.climbstat.presentation.ui.components.toposComponents.BoulderRankingListComponent

@Composable
fun BoulderDetailComponent(
    toposUiState: ToposUiState,
    boulder: Boulder,
    onExitClick: () -> Unit,
    onFlashClick: (String) -> Unit,
    onTopClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = boulder.image,
                contentDescription = "Image de la salle du bloc",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.picture_frame_svgrepo_com),
                error = painterResource(R.drawable.picture_frame_svgrepo_com),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Difficulté",
                        color = MaterialTheme.colorScheme.outlineVariant,
                        fontSize = 16.sp,
                        lineHeight = 16.sp
                    )
                    Text(
                        text = boulder.difficulty,
                        fontSize = 25.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Ouvert le",
                        color = MaterialTheme.colorScheme.outlineVariant,
                        fontSize = 16.sp,
                        lineHeight = 16.sp
                    )
                    Text(
                        text = "12/04/25",
                        fontSize = 25.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                BoulderDetailTopoComponent(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    text = "Top",
                    icon = R.drawable.check_circle_svgrepo_com,
                    onClick = { onTopClick(boulder.id) }
                )
                BoulderDetailTopoComponent(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    text = "Flash",
                    icon = R.drawable.flash_circle_2_svgrepo_com,
                    onClick = { onTopClick(boulder.id) }
                )
            }

            DescriptionComponent(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                description = boulder.description,
                tags = boulder.types
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.podium_svgrepo_com),
                        contentDescription = "Icone de classement",
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = "Classement",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                BoulderRankingListComponent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    toposUiState = toposUiState
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.cross_round_svgrepo_com),
            contentDescription = "Exit detail",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
                .clickable(
                    onClick = { onExitClick() }
                )
        )
    }
}