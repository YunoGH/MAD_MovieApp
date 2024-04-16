package com.example.movieappmad24.viewmodels

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

// Inherit from ViewModel class
class MoviesViewModel : ViewModel() {
    private val _movies = getMovies().toMutableStateList()
    val movies: List<Movie>
        get() = _movies

    val favoriteMovies: List<Movie>
        get() = _movies.filter { movie -> movie.isFavorite }

    fun toggleFavoriteMovie(movieId: String) = _movies.find { it.id == movieId }?.let { movie ->
        Log.i("MoviesViewModel", "toggleFavoriteMovie")
        movie.isFavorite = !movie.isFavorite
    }
}