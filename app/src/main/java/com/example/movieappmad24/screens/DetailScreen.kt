package com.example.movieappmad24.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.HorizontalScrollableImageView
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: String?,
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {
    movieId?.let {
        val movie = moviesViewModel.movies.first { it.id == movieId }

        Scaffold(
            topBar = {
                SimpleTopAppBar(title = movie.title) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                MovieRow(
                    movie = movie,
                    onFavoriteClick = { movieId -> moviesViewModel.toggleFavoriteMovie(movieId) },
                )
                Text(
                    text = "Movie Trailer",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                MovieTrailerView(movie = movie)
                HorizontalScrollableImageView(movie = movie)
            }
        }
    }
}

@Composable
fun MovieTrailerView(movie: Movie) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context)
    val resId = context.resources.getIdentifier("trailer_placeholder", "raw", context.packageName)
    val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$resId")
    DisposableEffect(exoPlayer) {
        val lifecycleObserver = androidx.lifecycle.LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = true
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }
    AndroidView(
        factory = { PlayerView(context).apply { player = exoPlayer } },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun rememberExoPlayer(context: Context): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build().also {

        }
    }
}