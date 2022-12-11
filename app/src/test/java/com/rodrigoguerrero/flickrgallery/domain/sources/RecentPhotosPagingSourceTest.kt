package com.rodrigoguerrero.flickrgallery.domain.sources

import androidx.paging.PagingSource
import com.rodrigoguerrero.flickrgallery.data.network.dto.PhotoDto
import com.rodrigoguerrero.flickrgallery.data.network.dto.PhotoPaginationDto
import com.rodrigoguerrero.flickrgallery.data.network.dto.PhotoResponseDto
import com.rodrigoguerrero.flickrgallery.data.network.services.PhotoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class RecentPhotosPagingSourceTest {

    private val service = mockk<PhotoService>()

    private lateinit var subject: RecentPhotosPagingSource

    @Before
    fun setup() {
        subject = RecentPhotosPagingSource(service)
    }

    @Test
    fun `When loading recent photos for the first time, then next key is 1 and photos are returned`() = runTest {
        val photos = listOf(PhotoDto(id = "id", secret = "secret", server = "server", title = "title"))
        coEvery { service.getRecentPhotos(any(), any()) } returns PhotoResponseDto(
            photoPaginationDto = PhotoPaginationDto(page = 0, total = 1, pages = 1, photosPerPage = 25, photos = photos),
            status = "ok"
        )

        val result = subject.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 25,
                placeholdersEnabled = false
            )
        )

        expectThat(result)
            .isA<PagingSource.LoadResult.Page<Int, PhotoDto>>()
            .and { get { data }.isEqualTo(photos) }
            .and { get { nextKey }.isEqualTo(1) }
            .and { get { prevKey }.isNull() }

        coVerify(exactly = 1) { service.getRecentPhotos(25, 0) }
    }

    @Test
    fun `When loading recent photos for the first time and call fails, then result is error`() = runTest {
        val exception = java.lang.Exception("exception")
        coEvery { service.getRecentPhotos(any(), any()) } throws exception

        val result = subject.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 25,
                placeholdersEnabled = false
            )
        )

        expectThat(result)
            .isA<PagingSource.LoadResult.Error<Int, PhotoDto>>()
            .and { get { throwable }.isEqualTo(exception) }
    }
}
