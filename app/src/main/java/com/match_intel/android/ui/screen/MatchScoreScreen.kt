package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.match_intel.android.viewmodel.MatchViewModel

@Composable
fun MatchScoreScreen(
    navController: NavController,
    matchId: String,
    player1Username: String,
    player2Username: String,
    viewModel: MatchViewModel = hiltViewModel()
) {
    val pointState by viewModel.point.collectAsState()

    if ((pointState?.player1Sets ?: 0) == 2 || (pointState?.player2Sets ?: 0) == 2) {
        navController.navigate("home")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Score", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        //if (pointState == null) {
            val point = pointState
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Player 1: @$player1Username",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "${point?.player1Sets ?: 0} ${point?.player1Games ?: 0} ${point?.player1Points ?:0}",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Right
                    )
                }
                Button(
                    modifier = Modifier.padding(bottom = 24.dp),
                    onClick = {
                        viewModel.addPoint(matchId, player1Username)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Point")
                }

                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {
                        viewModel.addPoint(matchId, player2Username)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Point")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Player 2: @$player2Username",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "${point?.player2Sets ?:0} ${point?.player2Games ?:0} ${point?.player2Points ?:0}",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Right
                    )
                }
            }
        //} else {
        //    CircularProgressIndicator()
        //}
    }
}
