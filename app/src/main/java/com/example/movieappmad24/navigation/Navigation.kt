package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.MovieList

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val moviesViewModel: MoviesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, moviesViewModel = moviesViewModel)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                moviesViewModel = moviesViewModel
            )
        }

        composable(route = Screen.WatchlistScreen.route) {
            WatchlistScreen(navController = navController, moviesViewModel = moviesViewModel)
        }
        composable(route = Screen.MyScreen.route) {
            MyScreen(viewModel = moviesViewModel)
        }
    }
}

@Composable
fun MyScreen(viewModel: MoviesViewModel) {
    MovieList(
        movies = viewModel.movies,
        onMovieClick = { movie ->
        },
        onFavoriteToggle = { movieId ->
            viewModel.toggleFavoriteMovie(movieId)
        }
    )
}
