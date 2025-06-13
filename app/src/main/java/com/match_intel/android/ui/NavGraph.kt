package com.match_intel.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.match_intel.android.ui.screen.HomeScreen
import com.match_intel.android.ui.screen.LoginScreen
import com.match_intel.android.ui.screen.RegisterScreen
import com.match_intel.android.viewmodel.AuthViewModel

@Composable
fun NavGraph(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val startDestination = if (authViewModel.token != null) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onRegisterClick = { navController.navigate("register") },
                viewModel = authViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home") },
                onLoginClick = { navController.navigate("login") },
                viewModel = authViewModel
            )
        }
        composable("home") {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }
    }
}
