package com.rodrigoguerrero.flickrgallery.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Photo(
    @PrimaryKey
    val id: String,
    val url: String,
    val title: String
)
