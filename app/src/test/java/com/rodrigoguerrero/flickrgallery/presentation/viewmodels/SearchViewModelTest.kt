package com.rodrigoguerrero.flickrgallery.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.CoroutineTestingRule
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import com.rodrigoguerrero.flickrgallery.domain.repositories.SearchRepository
import com.rodrigoguerrero.flickrgallery.domain.sources.SearchPagingSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestingRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val recentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm")
    private val otherRecentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm2")

    private val searchPagingSource = mockk<SearchPagingSource>()
    private val repository = mockk<SearchRepository>(relaxed = true) {
        coEvery { getRecentSearchTerms() } returns flowOf(
            listOf(recentSearchTerm, otherRecentSearchTerm)
        )
    }
    private lateinit var subject: SearchViewModel

    @Before
    fun setup() {
        subject = SearchViewModel(searchPagingSource, repository)
    }

    @Test
    fun `When initializing, then loading is false and search suggestions are loaded`() = runTest {
        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.recentSearchTerms.size).isEqualTo(2)
                that(result.recentSearchTerms[0]).isEqualTo("searchTerm")
                that(result.recentSearchTerms[1]).isEqualTo("searchTerm2")
            }
        }
    }

    @Test
    fun `When search is called, then isLoading is true and saveSearchTerm is called`() = runTest {
        subject.search("query")

        subject.uiState.test {
            val result = awaitItem()

            expectThat(result.isLoading).isTrue()

            coVerify(exactly = 1) { repository.saveSearchTerm("query") }
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
    fun `When updatePaginationLoading() is called, then paginationLoadingMoreItems is true`() = runTest {
        subject.updatePaginationLoading()

        subject.uiState.test {
            val result = awaitItem()
            expect {
                that(result.isLoading).isFalse()
                that(result.paginationLoadingMoreItems).isTrue()
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

    @Test
    fun `When onSearchValueChanged() is called, then state has the new query value`() = runTest {
        subject.onSearchValueChanged("newValue")

        subject.uiState.test {
            expectThat(awaitItem().searchQuery).isEqualTo("newValue")
        }
    }
}
