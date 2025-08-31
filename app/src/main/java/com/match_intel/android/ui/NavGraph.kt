package com.match_intel.android.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.match_intel.android.ui.screen.LoginScreen
import com.match_intel.android.ui.screen.MatchStatsScreen
import com.match_intel.android.ui.screen.RegisterScreen
import com.match_intel.android.ui.screen.UserDetailsScreen
import com.match_intel.android.viewmodel.AuthViewModel

@Composable
fun NavGraph(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val startDestination = if (authViewModel.token != null) "main" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("main") },
                onRegisterClick = { navController.navigate("register") },
                viewModel = authViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("main") },
                onLoginClick = { navController.navigate("login") },
                viewModel = authViewModel
            )
        }
        composable("main") {
            MainScaffold(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                navController = navController
            )
        }
        composable("matchDetails/{matchId}") { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: return@composable
            MatchStatsScreen(
                viewModel = hiltViewModel(),
                matchId = matchId
            )
        }
        composable("userDetails/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            UserDetailsScreen(
                username = username,
                viewModel = hiltViewModel(),
                homeViewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}
