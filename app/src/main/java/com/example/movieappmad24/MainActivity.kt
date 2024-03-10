package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme


// MAD-Lab02 -> AppBars, Expandable Movie Cards, Coil, Refactoring

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Alternatively use -> @Preview (no need to emulate a device)
                    MovieScreenContent()
                }
            }
        }
    }
}


// Icons stored separately for easier readability (if more are added later on)
@Composable
fun HomeIcon() {
    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "Home Button/Icon"
    )
}

@Composable
fun StarIcon() {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = "Watchlist Button/Icon"
    )
}

@Composable
fun FavoriteIcon() {
    Icon(
        tint = MaterialTheme.colorScheme.tertiary,
        imageVector = Icons.Default.FavoriteBorder,
        contentDescription = "Favourite Watchlist Button/Icon"
    )
}


// Adds scaffold -> used for setting up typical app layout with a top app bar and a bottom navigation bar as well as the main content area
@Composable
fun MovieScreenContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // Change title here
        topBar = { AppTopBar("Movie App") },
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // Adds body -> movies
            MovieListContent(movies = getMovies())
        }
    }
}

// @OptIn(ExperimentalMaterial3Api::class) is an experimental feature -> (might change or be removed in the future)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String?) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                // overwrites scaffold -> alt. remove text here and pass it through the function
                "Movie App MAD",
                color = Color.White // Change color here or alt. dynamically
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            //containerColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}


@Composable
fun MovieRowContent(movie: Movie) {
    // State to control the visibility of additional movie details
    var showDetails by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = ShapeDefaults.Medium,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                // changes images displayed based off of Movie.kt
                MovieImageContent(movie.images[2], "image of " + movie.title)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(13.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    FavoriteIcon()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MovieTitleAndArrow(movie.title, showDetails) {
                    showDetails = !showDetails
                }
            }
            // Animated visibility for additional movie details
            AnimatedVisibility(showDetails) {
                MovieInfoContent(movie)
            }
        }
    }
}

@Composable
fun MovieListContent(movies: List<Movie> = getMovies()) {
    LazyColumn {
        items(movies) { movie ->
            MovieRowContent(movie)
        }
    }
}

// Adds movie details (director, release year, plot, …) in an expandable column
@Composable
fun MovieInfoContent(movie: Movie) {
    Column(Modifier.padding(14.dp)) {
        Text("Released in: " + movie.year)
        Text("Genre: " + movie.genre)
        Text("Director: " + movie.director)

        // Space holder
        Divider(Modifier.padding(16.dp))
        Text("Plot: " + movie.plot)

        // Space holder
        Divider(Modifier.padding(14.dp))
        Text("Rating: " + movie.rating)

        // Space holder
        Divider(Modifier.padding(12.dp))
        Text("ID: " + movie.id)
    }
}

@Composable
fun MovieImageContent(source: String, description: String?) {
    // AsyncImage loads and displays images asynchronously using the Coil library
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = source,
        contentDescription = description ?: "MovieImage Desc.",
        //placeholder = painterResource(id = R.drawable.movie_image),
        contentScale = ContentScale.Crop
    )
}

// Adds the title of the movie as well as the arrow w/o "functionality"
@Composable
fun MovieTitleAndArrow(title: String, showDetails: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Icon(
            modifier = Modifier.clickable(onClick = onClick),
            imageVector = if (showDetails) Icons.Filled.KeyboardArrowDown
            else
                Icons.Default.KeyboardArrowUp, contentDescription = "Content Collapse"
        )
    }
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        var watchlistSelected by remember { mutableStateOf(false) }
        NavigationBarItem(
            selected = !watchlistSelected,
            onClick = { watchlistSelected = !watchlistSelected },
            label = { Text(text = "Home") },
            icon = { HomeIcon() }
        )
        NavigationBarItem(
            selected = watchlistSelected,
            onClick = { watchlistSelected = !watchlistSelected },
            label = { Text(text = "Watchlist") },
            icon = { StarIcon() }
        )
    }
}


// Doesn't emulate device on use
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MovieAppMAD24Theme {
//        MovieScreenContent()
//    }
//  }
//}