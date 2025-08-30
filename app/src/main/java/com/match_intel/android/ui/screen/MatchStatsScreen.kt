package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.MatchStatsDto
import com.match_intel.android.ui.components.CenteredMatchProgressBar
import com.match_intel.android.viewmodel.MatchDetailsUiState
import com.match_intel.android.viewmodel.MatchStatsViewModel

@Composable
fun MatchStatsScreen(
    viewModel: MatchStatsViewModel,
    matchId: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(matchId) {
        viewModel.loadMatch(matchId)
    }

    when (uiState) {
        is MatchDetailsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
        is MatchDetailsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("Error while loading data. Please try again.") }
        }
        is MatchDetailsUiState.Success -> {
            val match = (uiState as MatchDetailsUiState.Success).match
            MatchDetailsContent(match)
        }
    }
}

@Composable
private fun MatchDetailsContent(match: MatchDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // "vs" always centered
            Text(
                text = "vs",
                style = MaterialTheme.typography.labelLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${match.player1.firstName} ${match.player1.lastName}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "@${match.player1.username}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${match.player2.firstName} ${match.player2.lastName}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "@${match.player2.username}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                StatsBySet(match.set1Score ?: "0 : 0", match.matchStats, 1, match.set2Score != null)
            }
            if ((match.matchStats?.point?.player1Sets ?: 0) + (match.matchStats?.point?.player2Sets ?: 0) >= 1) {
                item {
                    StatsBySet(match.set2Score ?: "0 : 0", match.matchStats, 2, match.set3Score != null)
                }
            }
            if ((match.matchStats?.point?.player1Sets ?: 0) + (match.matchStats?.point?.player2Sets ?: 0) >= 2) {
                if (!match.finished || match.set3Score != null) {
                    item {
                        StatsBySet(match.set3Score ?: "0 : 0", match.matchStats, 3, match.finished)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsBySet(score: String, stats: MatchStatsDto?, set: Int, isSetFinished: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Set $set", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(score, style = MaterialTheme.typography.titleLarge)

            if (stats != null) {
                if (!isSetFinished) {
                    Text(
                        "${stats.point.player1Points} : ${stats.point.player2Points}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val player1ForcedErrors = when (set) {
                    1 -> stats.player1Set1ForcedErrors
                    2 -> stats.player1Set2ForcedErrors
                    3 -> stats.player1Set3ForcedErrors
                    else -> 0
                }
                val player1UnforcedErrors = when (set) {
                    1 -> stats.player1Set1UnforcedErrors
                    2 -> stats.player1Set2UnforcedErrors
                    3 -> stats.player1Set3UnforcedErrors
                    else -> 0
                }
                val player1Efficiency = when (set) {
                    1 -> stats.player1Set1Efficiency
                    2 -> stats.player1Set2Efficiency
                    3 -> stats.player1Set3Efficiency
                    else -> 0
                } ?: 0

                val player2ForcedErrors = when (set) {
                    1 -> stats.player2Set1ForcedErrors
                    2 -> stats.player2Set2ForcedErrors
                    3 -> stats.player2Set3ForcedErrors
                    else -> 0
                }
                val player2UnforcedErrors = when (set) {
                    1 -> stats.player2Set1UnforcedErrors
                    2 -> stats.player2Set2UnforcedErrors
                    3 -> stats.player2Set3UnforcedErrors
                    else -> 0
                }
                val player2Efficiency = when (set) {
                    1 -> stats.player2Set1Efficiency
                    2 -> stats.player2Set2Efficiency
                    3 -> stats.player2Set3Efficiency
                    else -> 0
                } ?: 0

                MatchStatsRow(
                    label = "Forced Errors",
                    player1Value = "${player1ForcedErrors ?: 0}",
                    player2Value = "${player2ForcedErrors ?: 0}"
                )
                MatchStatsRow(
                    label = "Unforced Errors",
                    player1Value = "${player1UnforcedErrors ?: 0}",
                    player2Value = "${player2UnforcedErrors ?: 0}"
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

                Text("Efficiency", style = MaterialTheme.typography.titleMedium)
                CenteredMatchProgressBar(player1Efficiency, player2Efficiency)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MatchStatsRow(
    label: String,
    player1Value: String,
    player2Value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = player1Value,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = player2Value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

