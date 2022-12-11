package com.rodrigoguerrero.flickrgallery.domain.repositories

import android.content.ContentResolver
import android.net.Uri
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSource
import com.rodrigoguerrero.flickrgallery.domain.workers.DownloadImageWorker
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_ID
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_NAME
import com.rodrigoguerrero.flickrgallery.domain.workers.PHOTO_URL
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val workManager: WorkManager,
    private val contentResolver: ContentResolver
) : PhotoRepository {

    override suspend fun isFavorite(id: String): Boolean {
        return photoDataSource.getFavorite(id) != null
    }

    override fun addFavorite(id: String, title: String, url: String) {
        val data = Data.Builder()

        data.apply {
            putString(PHOTO_NAME, title)
            putString(PHOTO_URL, url)
            putString(PHOTO_ID, id)
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val fileDownloadWorker = OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            fileDownloadWorker
        )
    }

    override suspend fun removeFavorite(id: String) {
        val photo = photoDataSource.getFavorite(id)
        photo?.let {
            photoDataSource.removeFavorite(photo.id)
            val uri = Uri.parse(photo.url)
            contentResolver.delete(uri, null, null)
        }
    }
}
