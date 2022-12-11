package com.rodrigoguerrero.flickrgallery.presentation.navigation

enum class Destinations(val route: String, val query: String = "") {
    RECENT_PHOTOS("recent-photos"),
    SEARCH("search"),
    FAVORITES("favorites"),
    DETAILS("details", "?url={url}&title={title}&id={id}")
}

const val urlParam = "url"
const val titleParam = "title"
const val idParam = "id"
