package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceView
import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.Navigator
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.BottomSheetView() {
    val sheetState = rememberModalBottomSheetState(true) { MainViewModel.state.currentWorkType.isNotNull() }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    val implementedWorkTypes = WorkType.all


    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.library))
            }
        )
        ScrollableTabRow (
            selectedTabIndex = WorkType.all.indexOf(viewModel.currentLibrary),
            edgePadding = 0.dp
        ) {
            implementedWorkTypes.forEach { workType ->
                Tab(
                    selected = workType == viewModel.currentLibrary,
                    onClick = { viewModel.currentLibrary = workType },
                    text = { Text(stringResource(id = workType.textRes)) }
                )
            }
        }
    }
}

@Composable
fun LibraryView.FabView() {
    FloatingActionButton(onClick = { navController?.navigate(Navigator.search.getNavRoute()) }) {
        Icon(Icons.Default.Add, null)
    }
}