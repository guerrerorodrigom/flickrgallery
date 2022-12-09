package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigoguerrero.flickrgallery.presentation.model.mainDestinations
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations

@Composable
fun MainBottomNavigation(
    currentRoute: String,
    navigate: (String) -> Unit
) {
    BottomNavigation {
        mainDestinations.forEach { destination ->
            BottomNavigationItem(
                selected = destination.route == currentRoute,
                onClick = { navigate(destination.route) },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainBottomNavigation() {
    MaterialTheme {
        MainBottomNavigation(currentRoute = Destinations.RECENT_PHOTOS.route, navigate = {})
    }
}
