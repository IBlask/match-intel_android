package com.match_intel.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.viewmodel.HomeViewModel

@Composable
fun MatchCard(
    match: MatchDto,
    onLikeClick: (String) -> Unit,
    viewModel: HomeViewModel
) {

    var showLikesDialog by remember { mutableStateOf(false) }

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

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.clickable { onLikeClick(match.id) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (match.likedByUser) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "Like",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Like",
                            modifier = Modifier.padding(start = 4.dp),
                            style = if (match.likedByUser) {
                                MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            } else {
                                MaterialTheme.typography.labelLarge
                            }
                        )
                    }
                }
                Text(
                    text = "${match.numberOfLikes} likes, ${match.numberOfComments} comments",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .clickable { showLikesDialog = true }
                )
            }
        }
    }


    if (showLikesDialog) {
        LikesCommentsDialog(
            matchId = match.id,
            onDismiss = { showLikesDialog = false },
            viewModel
        )
    }
}
