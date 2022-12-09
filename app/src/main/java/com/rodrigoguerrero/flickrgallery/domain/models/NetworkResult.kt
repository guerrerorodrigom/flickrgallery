package com.rodrigoguerrero.flickrgallery.domain.models

sealed class NetworkResult<out T: Any> {
    object Loading : NetworkResult<Nothing>()
    data class Success<out T: Any>(val data: T): NetworkResult<T>()
    data class Error(val throwable: Throwable?): NetworkResult<Nothing>()
}
