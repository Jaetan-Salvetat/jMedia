package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        SearchBar(
            query = viewModel.searchValue,
            onQueryChange = { viewModel.searchValue = it },
            onSearch = { viewModel.isSearchBarActive = true },
            active = viewModel.isSearchBarActive,
            onActiveChange = { viewModel.isSearchBarActive = !viewModel.isSearchBarActive },
            leadingIcon = {
                if (viewModel.isSearchBarActive) {
                    IconButton(onClick = { viewModel.isSearchBarActive = false }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                } else {
                    Icon(Icons.Filled.Search, null)
                }
            },
            trailingIcon = {
                if (viewModel.searchValue.isNotEmpty()) {
                    IconButton(onClick = { viewModel.searchValue = "" }) {
                        Icon(Icons.Filled.Clear, null)
                    }
                }
            },
            placeholder = { Text(R.string.search_library_placeholder.localized(MainViewModel.worksSize)) }) {
        }
    }
}