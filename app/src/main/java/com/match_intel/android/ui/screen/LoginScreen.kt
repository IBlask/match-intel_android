package com.match_intel.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.match_intel.android.viewmodel.AuthViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.clearError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())

        viewModel.error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = { viewModel.login(username, password, onLoginSuccess) }
        ) {
            Text("Login")
        }

        TextButton(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onRegisterClick
        ) {
            Text("Don't have an account? Register now!")
        }
    }
}
