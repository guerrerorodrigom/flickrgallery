package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rodrigoguerrero.flickrgallery.presentation.components.DetailsTopBar
import com.rodrigoguerrero.flickrgallery.presentation.components.PhotoDetails
import com.rodrigoguerrero.flickrgallery.presentation.viewmodels.DetailsViewModel

@Composable
fun PhotoDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    onClose: () -> Unit,
    id: String,
    url: String,
    title: String
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init(id)
    }

    Scaffold(
        topBar = {
            DetailsTopBar(
                onClose = onClose,
                isFavorite = state.isFavorite,
                isFavoriteClicked = {
                    if (state.isFavorite) {
                        viewModel.removeFromFavorites(id)
                    } else {
                        viewModel.addToFavorites(id, url, title)
                    }
                }
            )
        }
    ) {
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            PhotoDetails(
                imageUrl = url,
                title = title,
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
            )
        }
    }
}
