package com.example.movieappmad24.screens

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
import com.example.movieappmad24.ui.theme.Blueish80

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar("Movie App")
        },
        bottomBar = {
            HomeBottomBar(false, navController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            // display the list of movies on the home screen
            MovieList(movies = getMovies(), navController = navController)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopBar(text: String?) {
    CenterAlignedTopAppBar(
        title = {
            Text(text ?: "")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Blueish80,
            // containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun HomeBottomBar(watchlistSelected: Boolean, navController: NavController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        var isWatchlistSelected by remember { mutableStateOf(watchlistSelected) }
        NavigationBarItem(selected = !isWatchlistSelected,
            onClick = {
                isWatchlistSelected = false
                navController.popBackStack()
            },
            label = { Text(text = "Home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
        )
        NavigationBarItem(selected = isWatchlistSelected,
            onClick = {
                isWatchlistSelected = true
                navController.navigate(Route.Watchlist.route)
            },
            label = { Text(text = "Watchlist") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Watchlist"
                )
            }
        )
    }
}