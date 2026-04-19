package com.example.climbstat.presentation.ui.screen

import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.climbstat.domain.model.DifficultyTier
import com.example.climbstat.domain.model.GymStats
import com.example.climbstat.domain.usecase.state.GymStatsUiState
import com.example.climbstat.presentation.viewModel.TopoViewModel

@Composable
fun TopoScreen(viewModel: TopoViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) viewModel.loadStats()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    when (val state = uiState) {
        is GymStatsUiState.Loading -> CenteredBox { CircularProgressIndicator() }
        is GymStatsUiState.Empty -> CenteredBox {
            Text(
                text = "Aucune statistique pour le moment.\nCommence par valider un bloc dans une salle !",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
        }
        is GymStatsUiState.Error -> CenteredBox {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
        }
        is GymStatsUiState.Success -> StatsList(state.gymStats)
    }
}

@Composable
private fun CenteredBox(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
    }
}

@Composable
private fun StatsList(stats: List<GymStats>) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Statistiques par salle",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stats) { GymStatsCard(it) }
        }
    }
}

@Composable
private fun GymStatsCard(stats: GymStats) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stats.gymName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatMetric(label = "Tops", value = stats.totalTops.toString())
                StatMetric(label = "Flash rate", value = "${(stats.flashRate * 100).toInt()} %")
            }
            Spacer(modifier = Modifier.height(12.dp))
            val maxCount = (stats.topsByTier.values.maxOrNull() ?: 0).coerceAtLeast(1)
            TierBar("Facile", stats.topsByTier[DifficultyTier.EASY] ?: 0, maxCount, Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(6.dp))
            TierBar("Moyen", stats.topsByTier[DifficultyTier.MEDIUM] ?: 0, maxCount, Color(0xFFFF9800))
            Spacer(modifier = Modifier.height(6.dp))
            TierBar("Difficile", stats.topsByTier[DifficultyTier.HARD] ?: 0, maxCount, Color(0xFFF44336))
        }
    }
}

@Composable
private fun StatMetric(label: String, value: String) {
    Column {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TierBar(label: String, count: Int, maxCount: Int, barColor: Color) {
    val fillFraction = if (maxCount > 0) count.toFloat() / maxCount else 0f
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(72.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(14.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fillFraction)
                    .background(barColor)
            )
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .width(32.dp)
                .padding(start = 8.dp),
            textAlign = TextAlign.End
        )
    }
}
