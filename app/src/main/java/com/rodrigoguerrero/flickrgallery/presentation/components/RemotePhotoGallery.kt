package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.rodrigoguerrero.flickrgallery.presentation.model.Photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemotePhotoGallery(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<Photo>,
    isLoadingMore: Boolean,
    onClick: (String, String, String) -> Unit
) {
    PhotoGallery(
        modifier = modifier,
        content = {
            items(photos.itemCount) { index ->
                photos[index]?.let { photo ->
                    PhotoCard(
                        imageUrl = photo.url,
                        title = photo.title,
                        onClick = { onClick(photo.url, photo.title, photo.id) }
                    )
                }
            }
        },
        isLoadingMore = isLoadingMore
    )
}
