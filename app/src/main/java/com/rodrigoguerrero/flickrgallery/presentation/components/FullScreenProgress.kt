package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import com.rodrigoguerrero.flickrgallery.R

@Composable
fun FullScreenProgress(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) { }
    ) {
        Progress()
    }
}

@Composable
fun Progress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        color = MaterialTheme.colors.primary,
        modifier = modifier.size(dimensionResource(id = R.dimen.progress_size)),
        strokeWidth = dimensionResource(id = R.dimen.progress_border_size)
    )
}
