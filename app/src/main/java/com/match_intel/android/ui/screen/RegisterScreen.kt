package com.match_intel.android.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.match_intel.android.R
import com.match_intel.android.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onLoginClick: () -> Unit, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
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
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Application Logo",
                modifier = Modifier
                    .size(150.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(3f)
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") })
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") })
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") })
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Text(
                    text = viewModel.error ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        viewModel.register(
                            username,
                            email,
                            firstName,
                            lastName,
                            password,
                            onRegisterSuccess
                        )
                    }
                ) {
                    Text("Register")
                }

                TextButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = onLoginClick
                ) {
                    Text("Already have an account? Login here")
                }
            }
        }
    }
}
