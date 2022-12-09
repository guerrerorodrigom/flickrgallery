package com.rodrigoguerrero.flickrgallery.data.models

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
