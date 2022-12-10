package com.rodrigoguerrero.flickrgallery.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.rodrigoguerrero.flickrgallery.R

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchSuggestions: List<String>,
    onValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    var showSuggestions by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.top_bar_padding))
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchQuery)
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { showSuggestions = it.isFocused },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_hint),
                    style = MaterialTheme.typography.body1
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable { onValueChanged("") }
                    )
                }
            }
        )
        SearchSuggestionsMenu(
            searchSuggestions,
            showSuggestions,
            onValueChanged,
            onSearch,
            focusManager
        )
    }
}
