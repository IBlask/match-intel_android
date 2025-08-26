package com.match_intel.android.ui.page

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
fun HomePage(matchViewModel: HomeViewModel = hiltViewModel()) {
    val matches by matchViewModel.matches.collectAsState()
    LaunchedEffect(Unit) { matchViewModel.fetchFollowedMatches() }

    LazyColumn {
        items(matches) { match ->
            MatchCard(match)
        }
    }
}