package fr.jaetan.jmedia.app.library.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceView
import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.Navigator
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import kotlinx.coroutines.launch

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
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.library))
            }
        )
}


@Composable
fun LibraryView.FabView() {
    FloatingActionButton(onClick = { navController?.navigate(Navigator.search.getNavRoute()) }) {
        Icon(Icons.Default.Add, null)
    }
}