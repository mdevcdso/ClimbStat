package com.example.climbstat.presentation.ui.components.toposComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climbstat.R
import com.example.climbstat.domain.usecase.state.ToposUiState

@Composable
fun BoulderToposListComponent(
    modifier: Modifier,
    toposUiState: ToposUiState
) {

    when(toposUiState) {
        is ToposUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(20.dp).padding(16.dp))
        }
        is ToposUiState.Success -> {
            val topos = toposUiState.topos
            Column(
                modifier = modifier
            ) {
                if(topos.isEmpty()) {
                    Text(
                        text = "Encore aucun top sur ce bloc, sois le premier !",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                topos.forEachIndexed {index, topo ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                            ) {
                                if (index < 3) {
                                    Text(
                                        text = (index + 1).toString(),
                                        fontSize = 25.sp,
                                        lineHeight = 25.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                                Text(
                                    text = topo.userName,
                                    fontSize = 20.sp,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            if(topo.isFlash) {
                                Icon(
                                    painter = painterResource(id = R.drawable.flash_1_svgrepo_com),
                                    contentDescription = "Icone de flash",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = (topo.nbAttempts).toString(),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
        is ToposUiState.Error -> {
            Text(
                text = "Impossible de charger le classement",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
