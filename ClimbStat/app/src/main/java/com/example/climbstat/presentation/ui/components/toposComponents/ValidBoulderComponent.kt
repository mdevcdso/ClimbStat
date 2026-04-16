package com.example.climbstat.presentation.ui.components.toposComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climbstat.R
import com.example.climbstat.presentation.ui.components.boulderComponents.BoulderDetailTopoComponent

@Composable
fun ValidBoulderComponent(
    isUserTopBoulder: Boolean,
    onFlashClick:() -> Unit,
    onTopClick: () -> Unit
) {
    if(isUserTopBoulder){
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                //verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Vous avez déjà fait ce bloc !",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                )
                Text(
                    text = "Le : 12/04/25",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier
                )
            }

        }
    }
    else {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoulderDetailTopoComponent(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = "Top",
                icon = R.drawable.check_circle_svgrepo_com,
                onClick = { onTopClick() }
            )
            BoulderDetailTopoComponent(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = "Flash",
                icon = R.drawable.flash_circle_2_svgrepo_com,
                onClick = { onFlashClick() }
            )
        }
    }
}