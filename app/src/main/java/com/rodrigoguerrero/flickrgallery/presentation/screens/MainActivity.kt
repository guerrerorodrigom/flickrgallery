package com.rodrigoguerrero.flickrgallery.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.DETAILS
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.FAVORITES
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.RECENT_PHOTOS
import com.rodrigoguerrero.flickrgallery.presentation.navigation.Destinations.SEARCH
import com.rodrigoguerrero.flickrgallery.presentation.navigation.titleParam
import com.rodrigoguerrero.flickrgallery.presentation.navigation.urlParam
import com.rodrigoguerrero.flickrgallery.presentation.theme.FlickrGalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrGalleryTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = RECENT_PHOTOS.route
                ) {
                    composable(RECENT_PHOTOS.route) {
                        RecentPhotosScreen(
                            currentRoute = navController.currentDestination?.route.orEmpty(),
                            navigate = { navController.navigate(it) },
                            onPhotoClicked = { url, title ->
                                val encodedUrl =
                                    URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                                navController.navigate("${DETAILS.route}?url=$encodedUrl&title=$title")
                            }
                        )
                    }
                    composable(FAVORITES.route) {
                        Text(text = "favorites")
                    }
                    composable(SEARCH.route) {
                        SearchScreen(
                            currentRoute = navController.currentDestination?.route.orEmpty(),
                            navigate = { navController.navigate(it) },
                            onPhotoClicked = { url, title ->
                                val encodedUrl =
                                    URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                                navController.navigate("${DETAILS.route}?url=$encodedUrl&title=$title")
                            }
                        )
                    }
                    composable(
                        route = "${DETAILS.route}${DETAILS.query}",
                        arguments = listOf(
                            navArgument(name = urlParam) { type = NavType.StringType },
                            navArgument(name = titleParam) { type = NavType.StringType }
                        )
                    ) { entry ->
                        PhotoDetailsScreen(
                            url = entry.arguments?.getString(urlParam, "").orEmpty(),
                            title = entry.arguments?.getString(titleParam, "").orEmpty(),
                            onClose = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
