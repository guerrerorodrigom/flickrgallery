package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rodrigoguerrero.flickrgallery.presentation.components.FullScreenProgress
import com.rodrigoguerrero.flickrgallery.presentation.components.LocalPhotoGallery
import com.rodrigoguerrero.flickrgallery.presentation.components.MainBottomNavigation
import com.rodrigoguerrero.flickrgallery.presentation.components.NotFound
import com.rodrigoguerrero.flickrgallery.presentation.viewmodels.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    currentRoute: String,
    navigate: (String) -> Unit,
    onPhotoClicked: (String, String, String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    Scaffold(
        bottomBar = { MainBottomNavigation(currentRoute, navigate) }
    ) {
        when {
            state.isLoading -> FullScreenProgress()
            state.favorites.isEmpty() -> NotFound()
            else -> LocalPhotoGallery(
                photos = state.favorites,
                modifier = modifier.padding(it),
                isLoadingMore = false,
                onClick = onPhotoClicked
            )
        }
    }
}
