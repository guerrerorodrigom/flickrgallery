package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rodrigoguerrero.flickrgallery.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGallery(
    modifier: Modifier = Modifier,
    content: LazyStaggeredGridScope.() -> Unit,
    isLoadingMore: Boolean,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.photo_gallery_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.photo_gallery_item_separation)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.photo_gallery_item_separation)),
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
        if (isLoadingMore) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Progress()
            }
        }
    }
}
