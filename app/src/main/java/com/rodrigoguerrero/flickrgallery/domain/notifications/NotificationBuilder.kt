package com.rodrigoguerrero.flickrgallery.domain.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationBuilder(
    private val context: Context,
    private val channelId: String,
    private val notificationId: Int
) {

    companion object {
        const val DownloadFavoriteNotificationId = 1
    }

    private var channel: NotificationChannel? = null
    private var title: String? = null
    private var contentText: String? = null
    private var ongoing: Boolean? = null
    private var progress: Boolean?  = null
    @DrawableRes
    private var smallIconRes: Int? = null

    fun withTitle(title: String) = apply {
        this.title = title
    }

    fun withContentText(contentText: String) = apply {
        this.contentText = contentText
    }

    fun withOngoing() = apply {
        this.ongoing = true
    }

    fun withProgress() = apply {
        this.progress = true
    }

    fun withSmallIcon(@DrawableRes smallIconRes: Int) = apply {
        this.smallIconRes = smallIconRes
    }

    fun withChannel(
        name: String,
        descriptionText: String,
        importance: Int? = null
    ) = apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(
                channelId,
                name,
                importance ?: NotificationManager.IMPORTANCE_DEFAULT
            )
                .apply {
                    description = descriptionText
                }
        }
    }

    fun show() {
        createChannel()
        val notification = buildNotification()
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    private fun buildNotification(): Notification {
        val builder = NotificationCompat.Builder(context, channelId)

        if (progress == true) {
            builder.setProgress(0, 0, true)
        }

        return builder
            .set(title, builder::setContentTitle)
            .set(contentText, builder::setContentText)
            .set(ongoing, builder::setOngoing)
            .set(smallIconRes, builder::setSmallIcon)
            .build()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel?.let {
                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(it)
            }
        }
    }

    private fun <K, T> K.set(param: T?, setFunc: (T) -> K): K {
        param?.let(setFunc)
        return this@set
    }
}
