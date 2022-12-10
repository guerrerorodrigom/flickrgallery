package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.PopupProperties
import com.rodrigoguerrero.flickrgallery.R

@Composable
fun SearchSuggestionsMenu(
    searchSuggestions: List<String>,
    showSuggestions: Boolean,
    onValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusManager: FocusManager
) {
    DropdownMenu(
        expanded = searchSuggestions.isNotEmpty() && showSuggestions,
        onDismissRequest = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.top_bar_padding)),
        properties = PopupProperties(focusable = false)
    ) {
        searchSuggestions.forEach { suggestion ->
            DropdownMenuItem(
                onClick = {
                    onValueChanged(suggestion)
                    onSearch(suggestion)
                    focusManager.clearFocus()
                }
            ) {
                Text(text = suggestion)
            }
        }
    }
}
