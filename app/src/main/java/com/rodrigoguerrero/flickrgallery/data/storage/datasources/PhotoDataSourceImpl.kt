package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PhotoDataSourceImpl @Inject constructor(
    private val database: FlickrDatabase
) : PhotoDataSource {
    override suspend fun addFavorite(photo: Photo) {
        database.photosDao().addFavorite(photo)
    }

    override suspend fun getFavorites() = database.photosDao().getFavorites()

    override suspend fun removeFavorite(id: String) {
        database.photosDao().removeFavorite(id)
    }

    override suspend fun getFavorite(id: String): Photo? {
        return database.photosDao().getFavorite(id)
    }
}
