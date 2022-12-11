package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rodrigoguerrero.flickrgallery.presentation.components.ErrorComponent
import com.rodrigoguerrero.flickrgallery.presentation.components.FullScreenProgress
import com.rodrigoguerrero.flickrgallery.presentation.components.MainBottomNavigation
import com.rodrigoguerrero.flickrgallery.presentation.components.PhotoGallery
import com.rodrigoguerrero.flickrgallery.presentation.viewmodels.RecentViewModel

@Composable
fun RecentPhotosScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentViewModel = hiltViewModel(),
    currentRoute: String,
    navigate: (String) -> Unit,
    onPhotoClicked: (String, String, String) -> Unit
) {
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()

    val pagingState = photos.loadState

    Scaffold(
        bottomBar = { MainBottomNavigation(currentRoute, navigate) }
    ) {
        when {
            pagingState.refresh is LoadState.Loading -> viewModel.updateIsLoading()
            pagingState.refresh is LoadState.Error || pagingState.append is LoadState.Error -> {
                viewModel.updateIsError()
            }
            pagingState.append is LoadState.Loading -> viewModel.updatePaginationLoading()
            else -> viewModel.resetPaginationLoading()
        }

        when {
            state.isLoading -> FullScreenProgress()
            state.isError -> ErrorComponent(modifier = Modifier.padding(it)) { photos.retry() }
            else -> PhotoGallery(
                photos = photos,
                modifier = modifier.padding(it),
                isLoadingMore = state.paginationLoadingMoreItems,
                onClick = onPhotoClicked
            )
        }
    }
}
