package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.views.SearchView
import fr.jaetan.jmedia.app.work_type_choice.views.WorkTypeChoiceView
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SearchBar(
            query = viewModel.searchValue,
            onQueryChange = { viewModel.searchValue = it },
            onSearch = { /* TODO: Start the research */ },
            active = viewModel.searchBarIsActive,
            onActiveChange = { viewModel.searchBarIsActive = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = { Text(stringResource(R.string.research)) },
            content = { SearchView(viewModel.searchValue).Content() },
            trailingIcon = {
                if (!viewModel.searchBarIsActive) {
                    IconButton(onClick = { viewModel.showWorkTypeSelectorSheet = true }) {
                        Icon(imageVector = Icons.Default.Tune, contentDescription = null)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.BottomSheetView() {
    val sheetState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()

    val hide: () -> Unit = {
        scope.launch {
            sheetState.hide()
            viewModel.showWorkTypeSelectorSheet = false
        }
    }

    JBottomSheet(
        isVisible = viewModel.showWorkTypeSelectorSheet,
        dismiss = { viewModel.showWorkTypeSelectorSheet = false },
        state = sheetState,
        shouldBeFullScreen = true
    ) {
        WorkTypeChoiceView(hide).GetView(navController)
    }
}