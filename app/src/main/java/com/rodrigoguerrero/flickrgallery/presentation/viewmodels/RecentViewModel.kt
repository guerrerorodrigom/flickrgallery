package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rodrigoguerrero.flickrgallery.domain.sources.PAGE_SIZE
import com.rodrigoguerrero.flickrgallery.domain.sources.RecentPhotosSource
import com.rodrigoguerrero.flickrgallery.presentation.mappers.mapToUi
import com.rodrigoguerrero.flickrgallery.presentation.model.Photo
import com.rodrigoguerrero.flickrgallery.presentation.model.PhotoListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val recentPhotosSource: RecentPhotosSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoListUiState())
    val uiState: StateFlow<PhotoListUiState> = _uiState

    val photos: Flow<PagingData<Photo>> = Pager(
        pagingSourceFactory = { recentPhotosSource },
        config = PagingConfig(pageSize = PAGE_SIZE)
    )
        .flow
        .map { pagingData -> pagingData.map { dto -> dto.mapToUi() } }
        .cachedIn(viewModelScope)

    fun updatePaginationLoading() {
        _uiState.update {
            it.copy(
                paginationLoadingMoreItems = true,
                isLoading = false,
                isError = false
            )
        }
    }

    fun resetPaginationLoading() {
        _uiState.update { it.copy(paginationLoadingMoreItems = false) }
    }

    fun updateIsLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
                paginationLoadingMoreItems = false,
                isError = false
            )
        }
    }

    fun updateIsError() {
        _uiState.update {
            it.copy(
                isLoading = false,
                paginationLoadingMoreItems = false,
                isError = true
            )
        }
    }
}
