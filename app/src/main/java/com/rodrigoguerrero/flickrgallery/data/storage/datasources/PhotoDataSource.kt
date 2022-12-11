package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo

interface PhotoDataSource {
    suspend fun addFavorite(photo: Photo)

    suspend fun getFavorites(): List<Photo>

    suspend fun removeFavorite(id: String)

    suspend fun getFavorite(id: String): Photo?
}
