package com.rodrigoguerrero.flickrgallery.domain.repositories

import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.SearchDataSource
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryTest {

    private val recentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm")
    private val otherRecentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm2")

    private val dataSource = mockk<SearchDataSource>(relaxed = true) {
        coEvery { getRecentSearchTerms() } returns flowOf(
            listOf(recentSearchTerm, otherRecentSearchTerm)
        )
    }

    private lateinit var subject : SearchRepositoryImpl

    @Before
    fun setup() {
        subject = SearchRepositoryImpl(dataSource)
    }

    @Test
    fun `When saveSearchTerm() is called, then addSearchTerm() in data source is called`() = runTest {
        subject.saveSearchTerm("searchTerm")

        coVerify(exactly = 1) { dataSource.addSearchTerm("searchTerm") }
    }

    @Test
    fun `When getRecentSearchTerms() is called, then getRecentSearchTerms() in data source is called and results are returned`() = runTest {

        subject.getRecentSearchTerms().test {
            val result = awaitItem()
            expect {
                that(result.size).isEqualTo(2)
                that(result[0]).isEqualTo(recentSearchTerm)
                that(result[1]).isEqualTo(otherRecentSearchTerm)
            }
            coVerify(exactly = 1) { dataSource.getRecentSearchTerms() }
            awaitComplete()
        }
    }
}