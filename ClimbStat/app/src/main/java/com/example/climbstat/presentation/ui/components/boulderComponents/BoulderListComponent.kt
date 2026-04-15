package com.example.climbstat.presentation.ui.components.boulderComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.climbstat.R
import com.example.climbstat.domain.model.Boulder

@Composable
fun BoulderListComponent(
    modifier: Modifier,
    boulders: List<Boulder>,
    onBoulderClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(boulders.size) { index ->
            val boulder = boulders[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onBoulderClick(boulder.id)
                        }
                    )
            ) {
                HorizontalDivider(
                    modifier = Modifier.padding(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        AsyncImage(
                            model = boulder.image,
                            contentDescription = "Image du bloc ${boulder.difficulty}",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.crags_svgrepo_com),
                            error = painterResource(R.drawable.crags_svgrepo_com),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .width(60.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Text(
                            text = boulder.difficulty,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                        )
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Ouvert le",
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                        Text(
                            text = "12/04/25",
                            fontSize = 18.sp,
                            lineHeight = 18.sp
                        )
                    }

                }
            }
        }
    }
}