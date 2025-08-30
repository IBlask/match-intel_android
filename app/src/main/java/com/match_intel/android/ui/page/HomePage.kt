package com.match_intel.android.ui.page

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.match_intel.android.ui.components.MatchCard
import com.match_intel.android.viewmodel.HomeViewModel

@Composable
fun HomePage(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMatchClick: (String) -> Unit
) {
    val matches by homeViewModel.matches.collectAsState()
    Log.d("HomeViewModel", "Loaded matches: $matches")

    LaunchedEffect(Unit) { homeViewModel.fetchFollowedMatches() }

    LazyColumn {
        items(matches, key = { it.id }) { match ->
            MatchCard(
                match = match,
                onLikeClick = { id -> homeViewModel.onLikeClick(id) },
                onClick = { onMatchClick(match.id) },
                viewModel = homeViewModel
            )
        }
    }
}