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
class SearchPagingSourceTest {

    private val service = mockk<PhotoService>()

    private lateinit var subject: SearchPagingSource

    @Before
    fun setup() {
        subject = SearchPagingSource(service)
    }

    @Test
    fun `When loading search results for the first time, then next key is 1 and photos are returned`() = runTest {
        val photos = listOf(PhotoDto(id = "id", secret = "secret", server = "server", title = "title"))
        coEvery { service.search(any(), any(), any()) } returns PhotoResponseDto(
            photoPaginationDto = PhotoPaginationDto(page = 0, total = 1, pages = 1, photosPerPage = 25, photos = photos),
            status = "ok"
        )
        subject.setSearchQuery("query")

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

        coVerify(exactly = 1) { service.search(25, 0, "query") }
    }

    @Test
    fun `When loading search result photos call fails, then result is error`() = runTest {
        val exception = java.lang.Exception("exception")
        coEvery { service.search(any(), any(), any()) } throws exception

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
