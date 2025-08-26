package com.match_intel.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.match_intel.android.data.dto.MatchDto

@Composable
fun MatchCard(match: MatchDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // "vs" always centered
                Text(
                    text = "vs",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${match.player1.firstName} ${match.player1.lastName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "@${match.player1.username}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${match.player2.firstName} ${match.player2.lastName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "@${match.player2.username}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "${match.finalScore}", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(8.dp))

            match.player1Efficiency?.let { score1 ->
                match.player2Efficiency?.let { score2 ->
                    CenteredMatchProgressBar(score1, score2)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = if (match.finished) "" else "IN PROGRESS", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                Text(text = "${match.startDate} ${match.startTime}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
