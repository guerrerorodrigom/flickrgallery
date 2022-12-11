package com.rodrigoguerrero.flickrgallery.data.network.dto

import com.google.gson.annotations.SerializedName

data class PhotoPaginationDto(
    val page: Int,
    val pages: Int,
    @SerializedName("perpage")
    val photosPerPage: Int,
    val total: Int,
    @SerializedName("photo")
    val photos: List<PhotoDto>
)
