package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.domain.sources.RecentPhotosPagingSource
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RecentViewModelTest {

    private val recentPhotosPagingSource = mockk<RecentPhotosPagingSource>()
    private lateinit var subject: RecentViewModel

    @Before
    fun setup() {
        subject = RecentViewModel(recentPhotosPagingSource)
    }

    @Test
    fun `When initializing, then loading is true`() = runTest {
        subject.uiState.test {
            expectThat(awaitItem().isLoading).isTrue()
        }
    }

    @Test
    fun `When updatePaginationLoading() is called, then paginationLoadingMoreItems is true`() = runTest {
        subject.updatePaginationLoading()

        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.isError).isFalse()
                that(result.paginationLoadingMoreItems).isTrue()
            }
        }
    }

    @Test
    fun `When resetPaginationLoading() is called, then loading values are false`() = runTest {
        subject.resetPaginationLoading()

        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.paginationLoadingMoreItems).isFalse()
            }
        }
    }

    @Test
    fun `When updateIsLoading() is called, then loading is true`() = runTest {
        subject.updateIsLoading()

        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isTrue()
                that(result.paginationLoadingMoreItems).isFalse()
                that(result.isError).isFalse()
            }
        }
    }

    @Test
    fun `When updateIsError() is called, then isError is true`() = runTest {
        subject.updateIsError()

        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.paginationLoadingMoreItems).isFalse()
                that(result.isError).isTrue()
            }
        }
    }
}
