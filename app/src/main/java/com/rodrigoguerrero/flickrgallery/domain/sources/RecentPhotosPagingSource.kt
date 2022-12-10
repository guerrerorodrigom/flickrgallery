package com.rodrigoguerrero.flickrgallery.domain.sources

import com.rodrigoguerrero.flickrgallery.data.models.PhotoResponseDto
import com.rodrigoguerrero.flickrgallery.data.services.PhotoService
import javax.inject.Inject

class RecentPhotosSource @Inject constructor(
    private val service: PhotoService
) : BasePhotoListSource() {

    override suspend fun getPhotos(page: Int): PhotoResponseDto {
        return service.getRecentPhotos(page = page)
    }
}
