package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchDataSourceImpl @Inject constructor(
    private val database: FlickrDatabase
) : SearchDataSource {
    override suspend fun addSearchTerm(searchTerm: String) {
        database.recentSearchTermsDao().insertSearchTerm(RecentSearchTerm(searchTerm = searchTerm))
    }

    override suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>> {
        return database.recentSearchTermsDao().getRecentSearches()
    }
}
