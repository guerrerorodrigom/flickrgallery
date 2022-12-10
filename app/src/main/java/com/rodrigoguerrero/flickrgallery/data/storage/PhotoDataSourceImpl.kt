package com.rodrigoguerrero.flickrgallery.data.storage

import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PhotoDataSourceImpl @Inject constructor(
    private val database: FlickrDatabase
) : PhotoDataSource {
    override suspend fun addSearchTerm(searchTerm: String) {
        database.recentSearchTermsDao().insertSearchTerm(RecentSearchTerm(searchTerm = searchTerm))
    }

    override suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>> {
        return database.recentSearchTermsDao().getRecentSearches()
    }
}
