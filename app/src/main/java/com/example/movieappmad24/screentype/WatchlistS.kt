package com.example.movieappmad24.screentype

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad24.models.MovieList
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.navigation.Route
import com.example.movieappmad24.ui.theme.Blue80


// sets up the layout structure for the screen displaying the user's watchlist
@Composable
fun WatchlistScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WatchlistTopBar("Your Watchlist")
        },
        bottomBar = {
            WatchlistBottomBar(true, navController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            // displays a subset of movies from the watchlist
            MovieList(movies = getMovies().subList(1, 6), navController = navController)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WatchlistTopBar(text: String?) {
    CenterAlignedTopAppBar(
        title = {
            Text(text ?: "")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Blue80,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun WatchlistBottomBar(watchlistSelected: Boolean, navController: NavController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        // manages the state of whether the watchlist is currently selected or not
        var watchlistState by remember { mutableStateOf(watchlistSelected) }
        // inverts the watchlist selection state for the home screen navigation item
        NavigationBarItem(selected = !watchlistState,
            onClick = {
                watchlistState = !watchlistState
                navController.popBackStack()
            },
            label = { Text(text = "Home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "HomeButton"
                )
            }
        )
        NavigationBarItem(selected = watchlistState,
            onClick = {
                watchlistState = !watchlistState
                navController.navigate(Route.Watchlist.route)
            },
            label = { Text(text = "Watchlist") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "WatchlistButton"
                )
            }
        )
    }
}