package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSource
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.presentation.model.FavoritePhoto
import com.rodrigoguerrero.flickrgallery.presentation.model.FavoritesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites()
                .map { list ->
                    list.map { photo ->
                        FavoritePhoto(title = photo.title, id = photo.id, url = photo.url)
                    }
                }
                .collectLatest { favorites ->
                    _uiState.update { it.copy(favorites = favorites, isLoading = false) }
                }
        }
    }
}
