package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
    suspend fun addSearchTerm(searchTerm: String)

    suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>>
}
