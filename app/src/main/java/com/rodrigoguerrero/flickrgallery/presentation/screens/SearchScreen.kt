package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rodrigoguerrero.flickrgallery.presentation.components.ErrorComponent
import com.rodrigoguerrero.flickrgallery.presentation.components.FullScreenProgress
import com.rodrigoguerrero.flickrgallery.presentation.components.MainBottomNavigation
import com.rodrigoguerrero.flickrgallery.presentation.components.NotFound
import com.rodrigoguerrero.flickrgallery.presentation.components.PhotoGallery
import com.rodrigoguerrero.flickrgallery.presentation.components.SearchTextField
import com.rodrigoguerrero.flickrgallery.presentation.viewmodels.SearchViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    currentRoute: String,
    navigate: (String) -> Unit,
    onPhotoClicked: (String, String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()

    val pagingState = photos.loadState

    Scaffold(
        topBar = {
            SearchTextField(
                searchQuery = state.searchQuery,
                onValueChanged = viewModel::onSearchValueChanged,
                onSearch = { searchTerm ->
                    keyboardController?.hide()
                    viewModel.search(searchTerm)
                },
                searchSuggestions = state.recentSearchTerms
            )
        },
        bottomBar = { MainBottomNavigation(currentRoute, navigate) }
    ) {
        if (state.searchQuery.isNotEmpty()) {
            when {
                pagingState.refresh is LoadState.Loading -> viewModel.updateIsLoading()
                pagingState.refresh is LoadState.Error || pagingState.append is LoadState.Error -> {
                    viewModel.updateIsError()
                }
                pagingState.append is LoadState.Loading -> viewModel.updatePaginationLoading()
                pagingState.append is LoadState.NotLoading -> viewModel.resetPaginationLoading()
            }
        }

        when {
            state.isLoading -> FullScreenProgress()
            state.isError -> ErrorComponent(modifier = Modifier.padding(it)) { photos.retry() }
            photos.itemCount == 0 && state.searchQuery.isNotEmpty() -> NotFound()
            else -> PhotoGallery(
                photos = photos,
                modifier = modifier.padding(it),
                isLoadingMore = state.paginationLoadingMoreItems,
                onClick = onPhotoClicked
            )
        }
    }
}
