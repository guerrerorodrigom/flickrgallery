package com.rodrigoguerrero.flickrgallery.presentation.model

data class PhotoListUiState(
    val isLoading: Boolean = true,
    val paginationLoadingMoreItems: Boolean = false
)
