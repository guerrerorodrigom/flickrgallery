package com.rodrigoguerrero.flickrgallery.domain.repositories

interface PhotoRepository {
    fun addFavorite(id: String, title: String, url: String)

    suspend fun removeFavorite(id: String)

    suspend fun isFavorite(id: String): Boolean
}
