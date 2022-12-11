package com.rodrigoguerrero.flickrgallery.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.dao.PhotosDao
import com.rodrigoguerrero.flickrgallery.data.storage.dao.RecentSearchTermDao
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm

@Database(
    entities = [
        RecentSearchTerm::class,
        Photo::class
    ],
    version = 1
)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun recentSearchTermsDao(): RecentSearchTermDao

    abstract fun photosDao(): PhotosDao
}
