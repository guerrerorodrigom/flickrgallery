package com.rodrigoguerrero.flickrgallery.domain.workers

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rodrigoguerrero.flickrgallery.R
import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSource
import com.rodrigoguerrero.flickrgallery.data.storage.entities.Photo
import com.rodrigoguerrero.flickrgallery.domain.notifications.NotificationBuilder
import com.rodrigoguerrero.flickrgallery.domain.notifications.NotificationBuilder.Companion.DownloadFavoriteNotificationId
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileOutputStream
import java.net.URL

const val PHOTO_URL = "photoUrlParam"
const val PHOTO_NAME = "photoNameParam"
const val PHOTO_ID = "photoIdParam"

private const val CHANNEL_NAME = "FlickrGallery"
private const val CHANNEL_ID = "flickr_gallery_favorites_channel"

@HiltWorker
class DownloadImageWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val photoDataSource: PhotoDataSource,
    private val contentResolver: ContentResolver
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val url = inputData.getString(PHOTO_URL) ?: ""
        val title = inputData.getString(PHOTO_NAME) ?: ""
        val id = inputData.getString(PHOTO_ID) ?: ""
        val imageType = getImageType(url)

        if (url.isEmpty() || imageType.isEmpty() || id.isEmpty()
        ) {
            return Result.failure()
        }

        NotificationBuilder(
            channelId = CHANNEL_ID,
            context = context,
            notificationId = DownloadFavoriteNotificationId
        )
        .withChannel(CHANNEL_NAME, context.getString(R.string.notification_channel_description))
        .withContentText(context.getString(R.string.notification_description))
        .withTitle(context.getString(R.string.notification_title))
        .withSmallIcon(R.drawable.ic_launcher_foreground)
        .withOngoing()
        .withProgress()
        .show()

        val uri = downloadImage(name = id, type = imageType, url = url)

        NotificationManagerCompat.from(context).cancel(DownloadFavoriteNotificationId)

        return if (uri != null) {
            photoDataSource.addFavorite(Photo(id = id, url = uri.toString(), title = title))
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun getImageType(url: String): String {
        val lastIndex = url.lastIndexOf(".")
        return url.substring(lastIndex, url.length)
    }

    private fun downloadImage(name: String, type: String, url: String): Uri? {
        val mimeType = getMimeType(type)

        if (mimeType.isEmpty()) return null

        val fileName = "$name.$type"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveWithMediaStore(fileName, mimeType, url)
        } else {
            saveWithFile(fileName, url)
        }
    }

    private fun saveWithFile(name: String, url: String): Uri? {
        return try {
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                name
            )
            URL(url).openStream().use { input ->
                FileOutputStream(target).use { output -> input.copyTo(output) }
            }

            target.toUri()
        } catch (exception: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveWithMediaStore(
        name: String,
        mimeType: String,
        url: String
    ): Uri? {
        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Favorites")
            }

            val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            return if (uri != null) {
                URL(url).openStream().use { input ->
                    contentResolver.openOutputStream(uri)?.use { output ->
                        input.copyTo(output, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }
        } catch (exception: Exception) {
            return null
        }
    }

    private fun getMimeType(type: String) = when (type) {
        ".jpg" -> "image/jpg"
        ".jpeg" -> "image/jpg"
        ".png" -> "image/png"
        else -> ""
    }
}
