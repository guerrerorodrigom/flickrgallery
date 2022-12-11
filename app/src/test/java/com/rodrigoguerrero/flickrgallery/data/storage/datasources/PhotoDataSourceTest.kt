package com.rodrigoguerrero.flickrgallery.data.storage.datasources

import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.dao.PhotosDao
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDataSourceTest {

    private val photo = Photo(id = "id", title = "title", url = "url")
    private val otherPhoto = Photo(id = "id2", title = "title2", url = "url2")

    private val photosDao = mockk<PhotosDao>(relaxed = true) {
        every { getFavorites() } returns flowOf(listOf(photo, otherPhoto))
        every { getFavorite(any()) } returns otherPhoto
    }
    private val database = mockk<FlickrDatabase> {
        every { photosDao() } returns photosDao
    }
    private lateinit var subject: PhotoDataSourceImpl

    @Before
    fun setup() {
        subject = PhotoDataSourceImpl(database)
    }

    @Test
    fun `When calling addFavorite(), then addFavorite() is called in the photosDao`() = runTest {
        subject.addFavorite(photo)

        coVerify(exactly = 1) { photosDao.addFavorite(photo) }
    }

    @Test
    fun `When calling getFavorites(), then favorites list is returned and getFavorites() in photoDao is called`() = runTest {
        val favorites = subject.getFavorites()

        favorites.test {
            val result = awaitItem()

            coVerify(exactly = 1) { photosDao.getFavorites() }
            expect {
                that(result.size).isEqualTo(2)
                that(result[0]).isEqualTo(photo)
                that(result[1]).isEqualTo(otherPhoto)
            }

            awaitComplete()
        }
    }

    @Test
    fun `When calling removeFavorite(), then removeFavorite() is called in the photosDao`() = runTest {
        subject.removeFavorite("id")

        coVerify(exactly = 1) { photosDao.removeFavorite("id") }
    }

    @Test
    fun `When calling getFavorite(), then getFavorite() is called in the photosDao`() = runTest {
        val favorite = subject.getFavorite("id")

        expectThat(favorite).isEqualTo(otherPhoto)
        coVerify(exactly = 1) { photosDao.getFavorite("id") }
    }
}