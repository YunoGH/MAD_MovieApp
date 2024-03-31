package com.example.movieappmad24.screentype

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.models.AdditionalImages
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieRow
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.Blueishy80

// screen for displaying additional information about a movie
@Composable
fun InfoScreen(movieId: String?, navController: NavController) {
    // retrieve movie details based on the provided movieId
    val movie: Movie? = getMovies().find { it.id == movieId }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            InfoTopBar(movie?.title, navController = navController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            movie?.let {
                MovieRow(movie)
                AdditionalImages(movie)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InfoTopBar(title: String?, navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(title ?: "")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Blueishy80,
            //containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
        }
    )
}