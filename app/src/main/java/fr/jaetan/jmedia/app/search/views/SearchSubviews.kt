package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.extensions.scrollableTopAppBarBackground
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection

@Composable
fun SearchView.TopBarView() {
    Column {
        TopBarCell()
        FilterCell()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchView.TopBarCell() {
    val focusManager = LocalFocusManager.current
    val search = {
        viewModel.fetchWorks()
        focusManager.clearFocus()
    }

    TopAppBar(
        title = {
            TextField(
                value = viewModel.searchValue,
                onValueChange = { viewModel.searchValue = it },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.research)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = search, enabled = viewModel.searchIsEnabled) {
                        Icon(Icons.Default.Search, null)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { search() })
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    navController?.popBackStack()
                }
            ) {
                Icon(Icons.Default.ArrowBack, null)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchView.FilterCell() {
    val context = LocalContext.current

    Column(Modifier.scrollableTopAppBarBackground(scrollBehavior.state)) {
        LazyRow {
            item { SortCell() }

            item {
                Row(Modifier.padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    FilterChip(
                        selected = viewModel.filters.size == viewModel.implementedFilters.size,
                        onClick = { viewModel.filterHandler(context) },
                        label = { Text(stringResource(R.string.all)) }
                    )

                    Box(
                        Modifier
                            .padding(start = 10.dp)
                            .padding(end = 5.dp)) {
                        Divider(
                            Modifier
                                .width(1.dp)
                                .height(30.dp))
                    }
                }
            }

            items(viewModel.implementedFilters) {
                Box(Modifier.padding(start = 5.dp)) {
                    FilterChip(
                        selected = viewModel.filters.contains(it),
                        onClick = { viewModel.filterHandler(context, it) },
                        label = { Text(stringResource(it.textRes)) }
                    )
                }
            }

            item { Box(Modifier.width(10.dp)) }
        }
        Divider()
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
                    text = { Text(stringResource(it.textRes)) },
                    onClick = { viewModel.sort = it }
                )
            }

            Divider()

            SortDirection.all.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        DoneIconAnimated(viewModel.sortDirection == it)
                    },
                    text = { Text(stringResource(it.textRes)) },
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