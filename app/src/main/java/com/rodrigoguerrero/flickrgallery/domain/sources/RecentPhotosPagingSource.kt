package com.rodrigoguerrero.flickrgallery.domain.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rodrigoguerrero.flickrgallery.data.models.PhotoDto
import com.rodrigoguerrero.flickrgallery.data.services.PhotoService
import javax.inject.Inject

class RecentPhotosSource @Inject constructor(
    private val service: PhotoService
) : PagingSource<Int, PhotoDto>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDto> {
        return try {
            val page = params.key ?: 0
            val result = service.getRecentPhotos(page = page)
            val photos = result.photoPaginationDto.photos

            LoadResult.Page(
                data = photos,
                nextKey = getNextKey(result.photoPaginationDto.pages, page),
                prevKey = getPreviousKey(page)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private fun getNextKey(totalPages: Int, currentPage: Int): Int? {
        return if (currentPage == totalPages) {
            null
        } else {
            currentPage.plus(1).coerceAtMost(totalPages)
        }
    }

    private fun getPreviousKey(currentPage: Int): Int? {
        return if (currentPage == 0) {
            null
        } else {
            currentPage.minus(1).coerceAtLeast(0)
        }
    }
}
