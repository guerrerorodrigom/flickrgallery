package com.rodrigoguerrero.flickrgallery.presentation.model

data class FavoritesUiState(
    val favorites: List<FavoritePhoto> = emptyList(),
    val isLoading: Boolean = true
)
