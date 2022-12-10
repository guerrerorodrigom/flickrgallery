package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.domain.sources.PAGE_SIZE
import com.rodrigoguerrero.flickrgallery.domain.sources.SearchPagingSource
import com.rodrigoguerrero.flickrgallery.presentation.mappers.mapToUi
import com.rodrigoguerrero.flickrgallery.presentation.model.Photo
import com.rodrigoguerrero.flickrgallery.presentation.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPagingSource: SearchPagingSource,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val searchQuery: MutableStateFlow<String?> = MutableStateFlow(null)

    val photos: Flow<PagingData<Photo>> = searchQuery
        .filterNotNull()
        .flatMapLatest { query ->
            Pager(
                pagingSourceFactory = { searchPagingSource.apply { setSearchQuery(query) } },
                config = PagingConfig(pageSize = PAGE_SIZE)
            )
                .flow
                .map { pagingData -> pagingData.map { dto -> dto.mapToUi() } }
                .cachedIn(viewModelScope)
        }

    init {
        viewModelScope.launch {
            photoRepository
                .getRecentSearchTerms()
                .map { list -> list.map { term -> term.searchTerm } }
                .collectLatest { recentTerms ->
                    _uiState.update { it.copy(recentSearchTerms = recentTerms) }
                }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(isLoading = true) }
        searchQuery.update { query }
        viewModelScope.launch { photoRepository.saveSearchTerm(query) }
    }

    fun onSearchValueChanged(value: String) {
        _uiState.update { it.copy(searchQuery = value) }
    }

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
        _uiState.update { it.copy(paginationLoadingMoreItems = false, isLoading = false) }
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
