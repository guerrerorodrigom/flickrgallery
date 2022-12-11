package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.presentation.model.FavoritePhoto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val photo = Photo(id = "id", title = "title", url = "url")
    private val otherPhoto = Photo(id = "id2", title = "title2", url = "url2")

    private val repository = mockk<PhotoRepository> {
        coEvery { getFavorites() } returns flowOf(listOf(photo, otherPhoto))
    }

    private lateinit var subject: FavoritesViewModel

    @Before
    fun setup() {
        subject = FavoritesViewModel(repository)
    }

    @Test
    fun `When initializing, then state has loading as true`() = runTest {
        subject.uiState.test {
            expectThat(awaitItem().isLoading).isTrue()
        }
    }

    @Test
    fun `When calling init(), then state has favorites list and loading as false`() = runTest {
        subject.init()

        subject.uiState.test {
            val loading = awaitItem()
            expectThat(loading.isLoading).isTrue()
            expectThat(loading.favorites.size).isEqualTo(0)

            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.favorites.size).isEqualTo(2)
                that(result.favorites[0]).isEqualTo(FavoritePhoto(title = "title", id = "id", url = "url"))
                that(result.favorites[1]).isEqualTo(FavoritePhoto(title = "title2", id = "id2", url = "url2"))

            }
        }
    }
}