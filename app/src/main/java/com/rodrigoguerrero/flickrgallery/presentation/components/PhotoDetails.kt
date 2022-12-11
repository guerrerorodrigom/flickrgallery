package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rodrigoguerrero.flickrgallery.R

@Composable
fun PhotoDetails(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.None,
    imageUrl: String,
    title: String
) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = contentScale,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .defaultMinSize(minHeight = 200.dp)

        )
        Text(
            text = title.ifEmpty { stringResource(id = R.string.no_title) },
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.photo_details_text_padding)),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
