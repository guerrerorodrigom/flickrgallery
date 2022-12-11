package com.rodrigoguerrero.flickrgallery.domain.repositories

import android.content.ContentResolver
import android.net.Uri
import androidx.work.WorkManager
import app.cash.turbine.test
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSource
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
class PhotoRepositoryTest {

    private val photo = Photo(id = "id", title = "title", url = "url")
    private val otherPhoto = Photo(id = "id2", title = "title2", url = "url2")

    private val photoDataSource = mockk<PhotoDataSource>(relaxed = true) {
        coEvery { getFavorites() } returns flowOf(listOf(photo, otherPhoto))
        coEvery { getFavorite("id") } returns photo
        coEvery { getFavorite("id2") } returns null
    }
    private val workManager = mockk<WorkManager>()
    private val contentResolver = mockk<ContentResolver>(relaxed = true)
    private val photoUri = mockk<Uri>()

    private lateinit var subject: PhotoRepositoryImpl

    @Before
    fun setup() {
        mockkStatic(Uri::class)
        subject = PhotoRepositoryImpl(photoDataSource, workManager, contentResolver)
    }

    @Test
    fun `When calling getFavorites(), then favorites are returned and getFavorites() from datasource is called`() = runTest {
        subject.getFavorites().test {
            val result = awaitItem()

            expect {
                that(result.size).isEqualTo(2)
                that(result[0]).isEqualTo(photo)
                that(result[1]).isEqualTo(otherPhoto)
            }

            coVerify(exactly = 1) { photoDataSource.getFavorites() }
            awaitComplete()
        }
    }

    @Test
    fun `When a photo is favorite, then isFavorite() returns true`() = runTest {
        val result = subject.isFavorite("id")

        expectThat(result).isTrue()
        coVerify(exactly = 1) { photoDataSource.getFavorite("id") }
    }

    @Test
    fun `When a photo is not favorite, then isFavorite() returns false`() = runTest {
        val result = subject.isFavorite("id2")

        expectThat(result).isFalse()
        coVerify(exactly = 1) { photoDataSource.getFavorite("id2") }
    }

    @Test
    fun `When a photo is removed from favorites, then photo is removed from database and from content resolver`() = runTest {
        every { Uri.parse("url") } returns photoUri

        subject.removeFavorite("id")

        coVerify(exactly = 1) { photoDataSource.getFavorite("id") }
        coVerify(exactly = 1) { photoDataSource.removeFavorite("id") }
        coVerify(exactly = 1) { contentResolver.delete(photoUri, null, null) }
    }
}
