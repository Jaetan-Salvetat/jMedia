package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.ui.shared.VerticalWorksListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    val focusManager = LocalFocusManager.current

    val onQuit: () -> Unit = {
        focusManager.clearFocus()
        viewModel.isSearchBarActive = false
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SearchBar(
            query = viewModel.searchValue,
            onQueryChange = { viewModel.searchValue = it },
            onSearch = { viewModel.isSearchBarActive = true },
            active = viewModel.isSearchBarActive,
            onActiveChange = {
                if (it) {
                    viewModel.isSearchBarActive = true
                } else {
                    onQuit()
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
            ),
            leadingIcon = {
                if (viewModel.isSearchBarActive) {
                    IconButton(onClick = onQuit) {
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
            placeholder = { Text(R.string.search_library_placeholder.localized(0)) },
            content = { SearchBarContent(onQuit) }
        )

        IconButton(onClick = { /* TODO */ }, modifier = Modifier.statusBarsPadding()) {
            Icon(Icons.Rounded.Settings, null)
        }
    }
}

@Composable
private fun LibraryView.NavigateToSearchViewButton(onQuit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                navigateToSearchBab(viewModel.searchValue)
                onQuit()
            }
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(20.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.TravelExplore, null)

        Column(Modifier.padding(start = 8.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = R.string.search_media_online.localized(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(viewModel.searchValue)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LibraryView.SearchBarContent(onQuit: () -> Unit) {
    val mediasController = LocalMediaManager.current

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (viewModel.searchValue.isNotBlank()) {
            item { NavigateToSearchViewButton(onQuit) }

            items(mediasController.localMediasAsList, key = { it.id.toHexString() }) {
                VerticalWorksListItem(it, Modifier.animateItem(), MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            }
        }
    }
}
