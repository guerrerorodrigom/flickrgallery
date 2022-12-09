package com.rodrigoguerrero.flickrgallery.presentation.mappers

import com.rodrigoguerrero.flickrgallery.data.models.PhotoDto
import com.rodrigoguerrero.flickrgallery.presentation.model.Photo

fun PhotoDto.mapToUi(): Photo = Photo(title = title, id = id, secret = secret, server = server)
