package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.match_intel.android.ui.components.MatchCard
import com.match_intel.android.viewmodel.HomeViewModel
import com.match_intel.android.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(
    username : String,
    viewModel: UserDetailsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val userDetails by viewModel.userDetails.collectAsState()

    LaunchedEffect(username) {
        viewModel.loadUserDetails(username)
    }

    userDetails?.let { user ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.headlineMedium)
            Text(text = "@${user.username}", style = MaterialTheme.typography.headlineSmall)

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Row {
                        Text(
                            text = "Followers",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Following",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Row {
                        Text(
                            text = "${user.followers}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "${user.following}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }

                item {
                    Text(
                        text = "Matches: ${user.matches.size}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                items(user.matches) { match ->
                    MatchCard(
                        match = match,
                        onLikeClick = { id -> homeViewModel.onLikeClick(id) },
                        onClick = { navController.navigate("matchDetails/${match.id}") },
                        viewModel = homeViewModel
                    )
                }
            }
        }
    }
}