package fr.jaetan.jmedia.app.library.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import fr.jaetan.jmedia.app.search.views.SearchView
import fr.jaetan.jmedia.app.work_type_choice.views.WorkTypeChoiceView
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import kotlinx.coroutines.launch

@Composable
fun LibraryView.TopBarView() {
    SearchView().Content()
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