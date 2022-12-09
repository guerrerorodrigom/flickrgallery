package com.rodrigoguerrero.flickrgallery.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigoguerrero.flickrgallery.presentation.model.BottomNavDestination.Favorites
import com.rodrigoguerrero.flickrgallery.presentation.model.BottomNavDestination.Home
import com.rodrigoguerrero.flickrgallery.presentation.model.BottomNavDestination.Search
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.FAVORITES
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.RECENT_PHOTOS
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.SEARCH

sealed class BottomNavDestination(val route: String, val icon: ImageVector) {
    object Home : BottomNavDestination(route = RECENT_PHOTOS.route, icon = Icons.Default.Home)
    object Search : BottomNavDestination(route = SEARCH.route, icon = Icons.Default.Search)
    object Favorites : BottomNavDestination(route = FAVORITES.route, icon = Icons.Default.Favorite)
}

val mainDestinations = listOf(Home, Search, Favorites)
