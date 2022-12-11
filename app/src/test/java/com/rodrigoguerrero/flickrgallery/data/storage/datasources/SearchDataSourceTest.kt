package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.dao.RecentSearchTermDao
import com.rodrigoguerrero.flickrgallery.data.storage.entities.RecentSearchTerm
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class SearchDataSourceTest {

    private val recentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm")
    private val otherRecentSearchTerm = RecentSearchTerm(searchTerm = "searchTerm2")

    private val dao = mockk<RecentSearchTermDao>(relaxed = true) {
        every { getRecentSearches() } returns flowOf(
            listOf(recentSearchTerm, otherRecentSearchTerm)
        )
    }
    private val database = mockk<FlickrDatabase> {
        every { recentSearchTermsDao() } returns dao
    }
    private lateinit var subject: SearchDataSourceImpl

    @Before
    fun setup() {
        subject = SearchDataSourceImpl(database)
    }

    @Test
    fun `When calling addSearchTerm(), then addSearchTerm() is called in the dao`() = runTest {
        subject.addSearchTerm("searchTerm")

        coVerify(exactly = 1) { dao.insertSearchTerm(RecentSearchTerm(searchTerm = "searchTerm")) }
    }

    @Test
    fun `When calling getRecentSearchTerms(), then getRecentSearches() is called in the dao and results are returned`() =
        runTest {
            val recentTerms = subject.getRecentSearchTerms()

            recentTerms.test {
                val result = awaitItem()
                expect {
                    that(result.size).isEqualTo(2)
                    that(result[0]).isEqualTo(recentSearchTerm)
                    that(result[1]).isEqualTo(otherRecentSearchTerm)
                }
                coVerify(exactly = 1) { dao.getRecentSearches() }

                awaitComplete()
            }
        }
}