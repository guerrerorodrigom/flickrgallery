package com.rodrigoguerrero.flickrgallery.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rodrigoguerrero.flickrgallery.R
import com.rodrigoguerrero.flickrgallery.presentation.components.PhotoDetails

@Composable
fun PhotoDetailsScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    url: String,
    title: String
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.top_bar_padding)),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { onClose() }
                )
            }
        }
    ) {
        PhotoDetails(
            imageUrl = url,
            title = title,
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        )
    }
}
