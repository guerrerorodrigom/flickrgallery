package com.rodrigoguerrero.flickrgallery.data.network.dto

import com.google.gson.annotations.SerializedName

data class PhotoResponseDto(
    @SerializedName("photos")
    val photoPaginationDto: PhotoPaginationDto,
    @SerializedName("stat")
    val status: String
)
