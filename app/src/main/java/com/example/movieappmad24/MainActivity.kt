package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieappmad24.navigation.Navigation

// MAD-Lab02 -> AppBars, Expandable Movie Cards, Coil, Refactoring
// MAD-Lab03 -> DetailScreen, Navigation Refactoring, WatchlistScreen, App Refactoring
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // is in charge of routing the user to selected screen
            Navigation()
        }
    }
}