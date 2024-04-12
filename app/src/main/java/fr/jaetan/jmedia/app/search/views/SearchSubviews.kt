package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.extensions.scrollableTopAppBarBackground
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.works.shared.WorkType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView.TopBarView() {
    val mediaManager = LocalMediaManager.current
    val searchState by mediaManager.searchState.collectAsState()

    Column(Modifier.scrollableTopAppBarBackground(scrollBehavior.state)) {
        TopBarCell()
        FilterCell()

        if (searchState == ListState.Loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        } else {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp))
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchView.TopBarCell() {
    val mediaManager = LocalMediaManager.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    val search: () -> Unit = {
        coroutineScope.launch {
            focusManager.clearFocus()
            mediaManager.search(viewModel.searchValue, viewModel.filters)
        }
    }

    TopAppBar(
        title = {
            TextField(
                value = viewModel.searchValue,
                onValueChange = { viewModel.searchValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = { Text(R.string.research.localized()) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    IconButton(onClick = search, enabled = viewModel.searchIsEnabled) {
                        Icon(Icons.Default.Search, null)
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(viewModel.searchValue.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                        IconButton(onClick = { viewModel.searchValue = "" }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { search() })
            )
        },
        scrollBehavior = scrollBehavior,
    )

    SideEffect {
        focusRequester.requestFocus()
    }
}

@Composable
fun SearchView.FilterCell() {
    val context = LocalContext.current
    val mediaManager = LocalMediaManager.current
    val editFilters: (filter: WorkType?) -> Unit = { type ->
        if (type == null) {
            viewModel.filterHandler(context) { types ->
                types?.let { mediaManager.search(viewModel.searchValue, it) }
            }
        } else {
            viewModel.filterHandler(context, type) { types ->
                types?.let { mediaManager.search(viewModel.searchValue, it) }
            }
        }
    }

    Column {
        LazyRow {
            item { SortCell() }

            item {
                Row(Modifier.padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    FilterChip(
                        selected = viewModel.filters.size == viewModel.implementedFilters.size,
                        onClick = { editFilters(null) },
                        label = { Text(R.string.all.localized()) }
                    )

                    Box(
                        Modifier
                            .padding(start = 10.dp)
                            .padding(end = 5.dp)
                    ) {
                        VerticalDivider(Modifier.height(30.dp))
                    }
                }
            }

            items(viewModel.implementedFilters) {
                Box(Modifier.padding(start = 5.dp)) {
                    FilterChip(
                        selected = viewModel.filters.contains(it),
                        onClick = { editFilters(it) },
                        label = { Text(it.textRes.localized()) }
                    )
                }
            }

            item { Box(Modifier.width(10.dp)) }
        }
    }
}

@Composable
private fun SearchView.SortCell() {
    Column {
        IconButton(onClick = { viewModel.showSortMenu = true }) {
            Icon(Icons.Default.FilterList, null)
        }

        DropdownMenu(expanded = viewModel.showSortMenu, onDismissRequest = { viewModel.showSortMenu = false }) {
            Sort.all.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        DoneIconAnimated(viewModel.sort == it)
                    },
                    enabled = !(viewModel.filters.size > 1 && it == Sort.Default),
                    text = { Text(it.textRes.localized()) },
                    onClick = { viewModel.sort = it }
                )
            }

            HorizontalDivider()

            SortDirection.all.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        DoneIconAnimated(viewModel.sortDirection == it)
                    },
                    text = { Text(it.textRes.localized()) },
                    onClick = { viewModel.sortDirection = it }
                )
            }
        }
    }
}

@Composable
private fun DoneIconAnimated(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Icon(Icons.Default.Done, null)
    }
}