package com.rodrigoguerrero.flickrgallery.data.services

import com.rodrigoguerrero.flickrgallery.data.models.PhotoResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("services/rest?method=flickr.photos.getRecent")
    suspend fun getRecentPhotos(
        @Query("per_page") perPage: Int = 25,
        @Query("page") page: Int
    ): PhotoResponseDto
}
