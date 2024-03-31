package com.example.movieappmad24.models

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.navigation.Route

// mostly unchanged from previous exercise

@Composable
fun MovieList(movies: List<Movie> = getMovies(), navController: NavController) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie) { movieId ->
                navController.navigate(Route.Info.route.replace("{movieId}", movieId))
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {
    var showDetails by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onItemClick(movie.id) },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HttpImageOfMovie(movie.images[1], "image of " + movie.title)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title)
                Icon(
                    modifier = Modifier
                        .clickable {
                            showDetails = !showDetails
                        },
                    imageVector =
                    if (showDetails) Icons.Filled.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
                )
            }
            AnimatedVisibility(showDetails) {
                MovieInfo(movie)
            }
        }
    }
}

// taken from previous exercise
@Composable
fun HttpImageOfMovie(httpSource: String, description: String?) {
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = httpSource,
        contentDescription = description ?: "not available",
        placeholder = painterResource(id = R.drawable.movie_image),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieInfo(movie: Movie) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Director: " + movie.director)
        Text(text = "Released: " + movie.year)
        Text(text = "Genre: " + movie.genre)
        Text(text = "Actors: " + movie.actors)
        Text(text = "Rating: " + movie.rating)
        Divider(Modifier.padding(14.dp))
        Text(text = "Plot: " + movie.plot)
    }
}

@Composable
fun FurtherImages(movie: Movie) {
    LazyRow(
        modifier = Modifier
            .height(310.dp)
    ) {
        items(movie.images) { image ->
            Box(modifier = Modifier
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(12.dp))) {
                HttpImageOfMovie(
                    image, "image: " + movie.title
                )
            }
        }
    }
}
