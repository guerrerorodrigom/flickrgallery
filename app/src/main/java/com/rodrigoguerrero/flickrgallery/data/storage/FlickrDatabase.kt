package com.rodrigoguerrero.flickrgallery.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm

@Database(entities = [RecentSearchTerm::class], version = 1)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun recentSearchTermsDao(): RecentSearchTermDao
}
