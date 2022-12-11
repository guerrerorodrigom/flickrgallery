package com.rodrigoguerrero.flickrgallery.domain.repositories

import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.SearchDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
) : SearchRepository {
    override suspend fun saveSearchTerm(searchTerm: String) {
        dataSource.addSearchTerm(searchTerm)
    }

    override suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>> {
        return dataSource.getRecentSearchTerms()
    }
}
