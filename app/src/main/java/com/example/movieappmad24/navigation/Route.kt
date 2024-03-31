package com.example.movieappmad24.navigation

// in Kotlin a sealed class is a special kind of class that represents restricted class hierarchies -
// in this case representing routes in the application
sealed class Route(val route: String) {

    data object Info : Route("infoscreen/{movieId}")

    data object Home : Route("home")

    data object Watchlist : Route("watchlist")
}