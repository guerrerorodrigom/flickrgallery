package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rodrigoguerrero.flickrgallery.presentation.components.FullScreenProgress
import com.rodrigoguerrero.flickrgallery.presentation.components.PhotoGallery
import com.rodrigoguerrero.flickrgallery.presentation.viewmodels.RecentViewModel

@Composable
fun RecentPhotosScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentViewModel = hiltViewModel()
) {
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()

    val pagingState = photos.loadState
    when {
        pagingState.refresh is LoadState.Loading -> FullScreenProgress()
        pagingState.refresh is LoadState.Error || pagingState.append is LoadState.Error -> {}
        pagingState.append is LoadState.Loading -> viewModel.updatePaginationLoading()
        else -> viewModel.resetPaginationLoading()
    }

    PhotoGallery(
        photos = photos,
        modifier = modifier,
        isLoadingMore = state.paginationLoadingMoreItems
    )
}
