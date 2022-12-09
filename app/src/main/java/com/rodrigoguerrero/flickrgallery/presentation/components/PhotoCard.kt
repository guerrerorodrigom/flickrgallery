package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.rodrigoguerrero.flickrgallery.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.card_radius)),
        elevation = dimensionResource(id = R.dimen.card_elevation),
        backgroundColor = MaterialTheme.colors.surface,
        onClick = onClick
    ) {
        PhotoDetails(modifier, imageUrl, title)
    }
}
