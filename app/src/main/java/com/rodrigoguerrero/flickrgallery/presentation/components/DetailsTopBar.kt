package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rodrigoguerrero.flickrgallery.R

@Composable
fun DetailsTopBar(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    isFavoritesEnabled: Boolean,
    onClose: () -> Unit,
    isFavoriteClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.top_bar_padding))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier.clickable { onClose() }
        )

        if (isFavoritesEnabled) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = null,
                modifier = Modifier.clickable { isFavoriteClicked() },
                tint = if (isFavorite) {
                    MaterialTheme.colors.primary
                } else {
                    LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                }
            )
        }
    }
}
