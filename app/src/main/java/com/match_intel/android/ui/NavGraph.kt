package com.match_intel.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.match_intel.android.ui.screen.CreateMatchScreen
import com.match_intel.android.ui.screen.HomeScreen
import com.match_intel.android.ui.screen.LoginScreen
import com.match_intel.android.ui.screen.MatchScoreScreen
import com.match_intel.android.ui.screen.RegisterScreen
import com.match_intel.android.ui.screen.UserDetailScreen
import com.match_intel.android.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
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
            HomeScreen(navController = navController)
        }
        composable(
            route = "user_detail/{username}/{firstName}/{lastName}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType }
            )
        ) {
            val username = it.arguments?.getString("username") ?: ""
            val firstName = it.arguments?.getString("firstName") ?: ""
            val lastName = it.arguments?.getString("lastName") ?: ""

            UserDetailScreen(username, firstName, lastName)
        }
        composable("create_match") {
            CreateMatchScreen(navController)
        }
        composable(
            route = "match_score/{matchId}/{player1}/{player2}",
            arguments = listOf(
                navArgument("matchId") { type = NavType.StringType },
                navArgument("player1") { type = NavType.StringType },
                navArgument("player2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            val player1 = backStackEntry.arguments?.getString("player1") ?: ""
            val player2 = backStackEntry.arguments?.getString("player2") ?: ""

            MatchScoreScreen(
                navController = navController,
                matchId = matchId,
                player1Username = player1,
                player2Username = player2
            )
        }

    }
}
