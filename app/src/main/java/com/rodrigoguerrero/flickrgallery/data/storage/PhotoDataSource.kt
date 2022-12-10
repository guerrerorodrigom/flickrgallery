package com.rodrigoguerrero.flickrgallery.data.storage

import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

interface PhotoDataSource {
    suspend fun addSearchTerm(searchTerm: String)

    suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>>
}
