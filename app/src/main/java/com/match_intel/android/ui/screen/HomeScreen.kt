
package com.match_intel.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.match_intel.android.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    navController: NavController
) {
    val matches by homeViewModel.matches.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val users by homeViewModel.users.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row {
            Button(
                onClick = { navController.navigate("create_match") },
                modifier = Modifier.weight(1f).padding(bottom = 8.dp)
            ) {
                Text("New Match")
            }

            Button(
                onClick = {
                    homeViewModel.logout()
                    navController.navigate("login")
                },
                modifier = Modifier.weight(1f).padding(bottom = 8.dp)
            ) {
                Text("Logout")
            }
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                homeViewModel.searchUsers(searchText)
            },
            label = { Text("Search users...") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        if (users.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                LazyColumn {
                    items(users) { user ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    //showSearchResults = false
                                    searchText = ""
                                    navController.navigate("user_detail/${user.username}/${user.firstName}/${user.lastName}")
                                }
                                .padding(12.dp)
                        ) {
                            Text("${user.firstName} ${user.lastName}", fontWeight = FontWeight.Bold)
                            Text("@${user.username}", style = MaterialTheme.typography.bodySmall)
                        }
                        HorizontalDivider()
                    }
                }
            }
        }


        if (matches.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                //CircularProgressIndicator()
                Text("Nothing to show at the moment. Follow other users to see their matches.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(matches) { match ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 32.dp)
                            .background(Color(0xFFF5F5F5))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.weight(3f),
                                ) {
                                    Text(
                                        text = match.player1.firstName + " " + match.player1.lastName,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(
                                        text = "(${match.player1.username})",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text(
                                        text = "vs",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(3f),
                                ) {
                                    Text(
                                        text = match.player2.firstName + " " + match.player2.lastName,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(
                                        text = "(${match.player2.username})",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = match.finalScore.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
