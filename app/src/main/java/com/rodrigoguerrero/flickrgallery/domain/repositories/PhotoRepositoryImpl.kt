package com.rodrigoguerrero.flickrgallery.domain.repositories

import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm
import com.rodrigoguerrero.flickrgallery.data.storage.PhotoDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl @Inject constructor(
    private val dataSource: PhotoDataSource
) : PhotoRepository {
    override suspend fun saveSearchTerm(searchTerm: String) {
        dataSource.addSearchTerm(searchTerm)
    }

    override suspend fun getRecentSearchTerms(): Flow<List<RecentSearchTerm>> {
        return dataSource.getRecentSearchTerms()
    }
}
