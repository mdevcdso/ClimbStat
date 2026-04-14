package com.example.climbstat.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climbstat.R

@Composable
fun ClimbingGymListComponent(
    message: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Aucune donnée à afficher",
                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp, start = 8.dp, end = 8.dp),
                fontSize = 16.sp
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.crags_svgrepo_com),
            contentDescription = "Icône de sélection vide",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
                .padding(top = 32.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
    }
}