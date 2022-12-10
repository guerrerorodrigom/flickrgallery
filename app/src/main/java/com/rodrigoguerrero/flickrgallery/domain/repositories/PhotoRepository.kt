package com.rodrigoguerrero.flickrgallery.domain.repositories

import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun saveSearchTerm(searchTerm: String)

    suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>>
}
