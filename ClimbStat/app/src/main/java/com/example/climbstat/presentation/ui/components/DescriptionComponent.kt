package com.example.climbstat.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionComponent(
    modifier: Modifier,
    description: String,
    tags: List<String>,
) {
    Column(
        modifier = modifier
    ){
        Text(
            text = "Description",
            color = MaterialTheme.colorScheme.outlineVariant,
            fontSize = 16.sp,
            lineHeight = 16.sp
        )
        Text(
            modifier = Modifier,
            text = description,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            tags.forEach { tag ->
                Card(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 6.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tag,
                            fontSize = 15.sp,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }



}