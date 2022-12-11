package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.domain.workers.DownloadImageWorker
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_ID
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_NAME
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_URL
import com.rodrigoguerrero.flickrgallery.presentation.model.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun init(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = repository.isFavorite(id)
            _uiState.update { it.copy(isFavorite = isFavorite) }
        }
    }

    fun addToFavorites(id: String, url: String, title: String) {
        repository.addFavorite(id, title, url)
        _uiState.update { it.copy(isFavorite = true) }
    }

    fun removeFromFavorites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(id)
            _uiState.update { it.copy(isFavorite = false) }
        }
    }
}
