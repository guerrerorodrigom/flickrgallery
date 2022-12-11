package com.rodrigoguerrero.flickrgallery.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recent_search",
    indices = [Index(value = ["search_term"], unique = true)]
)
data class RecentSearchTerm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "search_term")
    val searchTerm: String
)
