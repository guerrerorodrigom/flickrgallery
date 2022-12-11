package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.CoroutineTestingRule
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestingRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<PhotoRepository>(relaxed = true)

    private lateinit var subject: DetailsViewModel

    @Before
    fun setup() {
        subject = DetailsViewModel(repository)
    }

    @Test
    fun `When initializing and photo is favorite, then uiState has correct favorite value`() = runTest {
        coEvery { repository.isFavorite(any()) } returns true

        subject.init("id")

        subject.uiState.test {
            val result = awaitItem()
            expectThat(result.isFavorite).isTrue()
        }
    }

    @Test
    fun `When initializing and photo is not favorite, then uiState has correct favorite value`() = runTest {
        coEvery { repository.isFavorite(any()) } returns false

        subject.init("id")

        subject.uiState.test {
            val result = awaitItem()
            expectThat(result.isFavorite).isFalse()
        }
    }

    @Test
    fun `When adding to favorites, then addFavorite() in repository is called, and state has favorite as true`() = runTest {
        subject.addToFavorites("id", "url", "title")

        subject.uiState.test {
            val result = awaitItem()
            expectThat(result.isFavorite).isTrue()

            coVerify(exactly = 1) { repository.addFavorite("id", "title", "url") }
        }
    }

    @Test
    fun `When removing from favorites, then removeFavorite() in repository is called, and state has favorite as false`() = runTest {
        subject.removeFromFavorites("id")

        subject.uiState.test {
            val result = awaitItem()
            expectThat(result.isFavorite).isFalse()

            coVerify(exactly = 1) { repository.removeFavorite("id") }
        }
    }
}
