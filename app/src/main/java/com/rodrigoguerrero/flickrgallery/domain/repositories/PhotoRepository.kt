package com.rodrigoguerrero.flickrgallery.domain.repositories

import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun addFavorite(id: String, title: String, url: String)

    suspend fun removeFavorite(id: String)

    suspend fun isFavorite(id: String): Boolean

    suspend fun getFavorites(): Flow<List<Photo>>
}
