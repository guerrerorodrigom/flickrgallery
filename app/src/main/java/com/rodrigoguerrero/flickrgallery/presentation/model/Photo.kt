package com.rodrigoguerrero.flickrgallery.presentation.model

data class Photo(
    val title: String,
    val id: String,
    private val secret: String,
    private val server: String
) {
    private val baseUrl = "https://live.staticflickr.com/"
    val url: String = "$baseUrl$server/${id}_${secret}_n.jpg"
}
