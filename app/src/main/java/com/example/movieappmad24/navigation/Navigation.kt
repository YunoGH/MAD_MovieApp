package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.InfoScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen


// composable function for defining navigation within the application
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {

        composable(route = Route.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Route.Watchlist.route) {
            WatchlistScreen(navController = navController)
        }
        composable(
            route = Route.Info.route,
            arguments = listOf(navArgument(name = "movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            InfoScreen(movieId = backStackEntry.arguments?.getString("movieId"), navController)
        }
    }
}