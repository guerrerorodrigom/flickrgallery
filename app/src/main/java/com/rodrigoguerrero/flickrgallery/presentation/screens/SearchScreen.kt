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
import com.rodrigoguerrero.flickrgallery.presentation.components.FullScreenProgress
import com.rodrigoguerrero.flickrgallery.presentation.components.MainBottomNavigation
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
    when {
        pagingState.refresh is LoadState.Loading -> FullScreenProgress()
        pagingState.refresh is LoadState.Error || pagingState.append is LoadState.Error -> {}
        pagingState.append is LoadState.Loading -> viewModel.updatePaginationLoading()
        else -> viewModel.resetPaginationLoading()
    }

    Scaffold(
        topBar = {
            SearchTextField(
                searchQuery = state.searchQuery,
                onValueChanged = viewModel::onSearchValueChanged,
                onSearch = { searchTerm ->
                    keyboardController?.hide()
                    viewModel.search(searchTerm)
                }
            )
        },
        bottomBar = { MainBottomNavigation(currentRoute, navigate) }
    ) {
        when {
            state.isLoading -> FullScreenProgress()
            else -> PhotoGallery(
                photos = photos,
                modifier = modifier.padding(it),
                isLoadingMore = state.paginationLoadingMoreItems,
                onClick = onPhotoClicked
            )
        }
    }
}
