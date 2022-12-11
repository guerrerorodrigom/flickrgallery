package com.rodrigoguerrero.flickrgallery.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Insert(onConflict = REPLACE)
    suspend fun addFavorite(photo: Photo)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<Photo>

    @Query("SELECT * FROM favorites WHERE id=:id")
    fun getFavorite(id: String): Photo?

    @Query("DELETE FROM favorites WHERE id=:id")
    fun removeFavorite(id: String)
}
