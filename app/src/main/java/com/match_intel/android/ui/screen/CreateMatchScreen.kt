package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.match_intel.android.viewmodel.MatchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateMatchScreen(navController: NavController, viewModel: MatchViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var player1 by remember { mutableStateOf(TextFieldValue("")) }
    var player2 by remember { mutableStateOf(TextFieldValue("")) }
    var server by remember { mutableStateOf(TextFieldValue("")) }
    var visibility by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val match by viewModel.match.collectAsState()

        TextField(value = player1, onValueChange = { player1 = it }, label = { Text("Player 1") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = player2, onValueChange = { player2 = it }, label = { Text("Player 2") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = server, onValueChange = { server = it }, label = { Text("Initial Server") })
        Spacer(modifier = Modifier.height(8.dp))
        PrivacyDropdown(
            selectedValue = visibility,
            onValueChange = { visibility = it }
        )
        //TextField(value = visibility, onValueChange = { visibility = it }, label = { Text("Visibility (0/1)") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                viewModel.createMatch(
                    player1.text,
                    player2.text,
                    server.text,
                    visibility ?: 0
                )
                delay(500)
                if (match != null) navController.navigate("match_score/${match!!.matchId}/${player1.text}/${player2.text}")
            }
        }) {
            Text("Create Match")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyDropdown(
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    // Mapa prikazano -> interno
    val privacyOptions = mapOf(
        "PRIVATE" to 0,
        "FOLLOWERS" to 1,
        "PUBLIC" to 2
    )

    // Obrnuta mapa: interno -> prikazano
    val reverseMap = privacyOptions.entries.associate { (k, v) -> v to k }

    var expanded by remember { mutableStateOf(false) }
    val selectedLabel = reverseMap[selectedValue] ?: "UNKNOWN"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Privacy") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            privacyOptions.forEach { (label, value) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onValueChange(value)
                        expanded = false
                    }
                )
            }
        }
    }
}

