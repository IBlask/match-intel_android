package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.match_intel.android.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    username: String,
    firstName: String,
    lastName: String,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val matches by viewModel.matches.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMatches(username)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("$firstName $lastName", style = MaterialTheme.typography.headlineSmall)
        Text("@$username", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)

        Button(
            onClick = { viewModel.sendFollowRequest(username) },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Follow")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(matches) { match ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("${match.player1.firstName} (@${match.player1.username}) vs ${match.player2.firstName} (@${match.player2.username})")
                    Text("Score: ${match.finalScore}")
                }
                HorizontalDivider()
            }
        }
    }
}
