package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.shared.VerticalWorksListItem
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import fr.jaetan.jmedia.ui.widgets.JScaledContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    TopAppBar(
        title = { SearchBar() },
        actions = {
            IconButton(onClick = {}, modifier = Modifier.padding(start = 16.dp, end = 8.dp)) {
                Icon(Icons.Rounded.FilterList, null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryView.WorksListBottomSheet() {
    val state = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()

    val hide: () -> Unit = {
        scope.launch {
            state.hide()
            viewModel.bottomSheetWorkType = null
        }
    }

    JBottomSheet(
        isVisible = viewModel.bottomSheetWorkType.isNotNull(),
        dismiss = hide,
        shouldBeFullScreen = true,
        state = state
    ) {
        val type = viewModel.bottomSheetWorkType!!
        val controller = MainViewModel.worksController.getController(type)

        if (controller.localWorks.isEmpty()) hide()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${type.titleRes.localized()} (${controller.localWorks.size})")

            JScaledContent(onPressed = hide) {
                Icon(Icons.Default.Clear, null)
            }
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(controller.localWorks, key = { it.id.toHexString() }) {
                VerticalWorksListItem(work = it, modifier = Modifier.animateItemPlacement(), isShowingTag = false)
            }

            item { Spacer(Modifier.weight(1f)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.SearchBottomSheet() {
    val focusManager = LocalFocusManager.current
    val bottomSheetState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()

    val onQuit: () -> Unit = {
        focusManager.clearFocus()
        viewModel.isSearchBarActive = false
    }

    val dismiss: () -> Unit = {
        focusManager.clearFocus()
        scope.launch {
            bottomSheetState.hide()
            viewModel.isSearchBarActive = false
        }
    }

    JBottomSheet(
        viewModel.isSearchBarActive,
        dismiss = dismiss,
        shouldBeFullScreen = true,
        state = bottomSheetState
    ) {
        SearchBarContent(
            onQuit = onQuit,
            dismiss = dismiss
        )
    }
}

@Composable
private fun LibraryView.SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp), CircleShape)
            .clip(CircleShape)
            .clickable { viewModel.isSearchBarActive = true }
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, null)
        Text(R.string.research.localized(), color = MaterialTheme.colorScheme.outline, fontSize = 17.sp)
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun LibraryView.SearchBarContent(onQuit: () -> Unit, dismiss: () -> Unit) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            Column {
                TopAppBar(
                    title = {
                        TextField(
                            value = viewModel.searchValue,
                            onValueChange = { viewModel.searchValue = it },
                            placeholder = { Text(R.string.research.localized()) },
                            modifier = Modifier.focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    },
                    actions = {
                        IconButton(onClick = dismiss) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }

        if (viewModel.searchValue.isNotBlank()) {
            item { NavigateToSearchViewButton(onQuit) }

            items(viewModel.filteredWorks, key = { it.id.toHexString() }) {
                VerticalWorksListItem(it, Modifier.animateItemPlacement(), MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}