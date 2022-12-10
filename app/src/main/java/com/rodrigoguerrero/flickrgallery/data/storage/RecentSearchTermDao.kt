package com.rodrigoguerrero.flickrgallery.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rodrigoguerrero.flickrgallery.data.models.entities.RecentSearchTerm
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchTermDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertSearchTerm(searchTerm: RecentSearchTerm)

    @Query("SELECT * FROM recent_search")
    fun getRecentSearches(): Flow<List<RecentSearchTerm>>
}
