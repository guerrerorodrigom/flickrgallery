package com.rodrigoguerrero.flickrgallery.presentation.model

data class PhotoListUiState(
    val paginationLoadingMoreItems: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = true
)
