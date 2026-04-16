package com.example.climbstat.presentation.ui.components.boulderComponents

import android.R.bool
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.climbstat.R
import com.example.climbstat.domain.model.Boulder
import com.example.climbstat.domain.usecase.state.ToposUiState
import com.example.climbstat.presentation.ui.components.DescriptionComponent
import com.example.climbstat.presentation.ui.components.toposComponents.BoulderRankingListComponent
import com.example.climbstat.presentation.ui.components.toposComponents.NewTopAlertDialogComponent
import com.example.climbstat.presentation.ui.components.toposComponents.ValidBoulderComponent

@Composable
fun BoulderDetailComponent(
    toposUiState: ToposUiState,
    boulder: Boulder,
    isUserTopBoulder: Boolean,
    onExitClick: () -> Unit,
    onTopClick: (String, String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var isFlash by remember { mutableStateOf(false) }
    var nbAttemptInputText by remember { mutableStateOf("1") }
    var commentInputText by remember { mutableStateOf("") }

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
            ValidBoulderComponent(
                isUserTopBoulder = isUserTopBoulder,
                onFlashClick = {
                    isFlash = true
                    showDialog = true
                },
                onTopClick = {
                    showDialog = true
                }
            )

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
        if (showDialog) {
            NewTopAlertDialogComponent(
                isFlash = isFlash,
                nbAttemptInputText = nbAttemptInputText,
                commentInputText = commentInputText,
                onNbAttemptChange = { nbAttemptInputText = it },
                onCommentChange = { commentInputText = it },
                onConfirm = {
                    val nbAttempt = nbAttemptInputText
                    if(isFlash) {
                        nbAttemptInputText = "1"
                    }
                    onTopClick(nbAttempt,commentInputText)
                    showDialog = false
                    isFlash = false
                    nbAttemptInputText = ""
                    commentInputText = ""
                },
                onDismiss = {
                    showDialog = false
                    isFlash = false
                    nbAttemptInputText = ""
                    commentInputText = ""
                }
            )
        }
    }
}