package com.rodrigoguerrero.flickrgallery.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations
import com.rodrigoguerrero.flickrgallery.presentation.theme.FlickrGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrGalleryTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.RECENT_PHOTOS.route
                ) {
                    composable(Destinations.RECENT_PHOTOS.route) { RecentPhotosScreen() }
                }
            }
        }
    }
}
