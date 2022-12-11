package com.rodrigoguerrero.flickrgallery.domain.repositories

import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun saveSearchTerm(searchTerm: String)

    suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>>
}
