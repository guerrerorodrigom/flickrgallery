package com.rodrigoguerrero.flickrgallery.presentation.model

data class SearchUiState(
    val isLoading: Boolean = false,
    val paginationLoadingMoreItems: Boolean = false,
    val searchQuery: String = "",
    val recentSearchTerms: List<String> = emptyList(),
    val isError: Boolean = false
)
