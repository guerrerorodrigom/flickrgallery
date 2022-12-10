package com.rodrigoguerrero.flickrgallery.domain.sources

import com.rodrigoguerrero.flickrgallery.data.models.PhotoResponseDto
import com.rodrigoguerrero.flickrgallery.data.services.PhotoService
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val service: PhotoService
) : BasePhotoListSource() {

    private var query: String = ""

    fun setSearchQuery(query: String) {
        this.query = query
    }

    override suspend fun getPhotos(page: Int): PhotoResponseDto {
        return service.search(page = page, text = query)
    }
}
