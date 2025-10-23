package com.example.cataloguedejeux.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cataloguedejeux.ui.screens.AccountScreen
import com.example.cataloguedejeux.ui.screens.AuthScreen
import com.example.cataloguedejeux.ui.screens.GameDetailsScreen
import com.example.cataloguedejeux.ui.screens.HomeScreen
import com.example.cataloguedejeux.viewmodel.AuthViewModel
import com.example.cataloguedejeux.viewmodel.GameViewModel

/**
 * Le NavGraph est le composant qui gère la navigation dans l'application.
 */
@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    val authViewModel: AuthViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()
    val user = authViewModel.user.value

    NavHost(
        navController = navController, 
        startDestination = if (user != null) "home" else "auth",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("auth") {
            AuthScreen(onSignInSuccess = { navController.navigate("home") })
        }
        
        composable("home") {
            HomeScreen(navController, gameViewModel)
        }

        composable(
            route = "gameDetails/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.StringType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val game = gameViewModel.getGameById(gameId!!)
            if (game != null) {
                GameDetailsScreen(game, navController)
            }
        }

        composable("account") {
            AccountScreen(navController)
        }
    }
}