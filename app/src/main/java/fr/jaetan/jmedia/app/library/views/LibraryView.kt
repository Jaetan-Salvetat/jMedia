package fr.jaetan.jmedia.app.library.views

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import fr.jaetan.jmedia.app.library.LibraryViewModel
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceViewModel
import fr.jaetan.jmedia.app.work_type_choice.views.WorkTypeChoiceView
import fr.jaetan.jmedia.ui.Screen
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import kotlinx.coroutines.launch

class LibraryView: Screen<LibraryViewModel>() {
    override val viewModel = LibraryViewModel()

    @Composable
    override fun Content() {
        Button(onClick = { viewModel.showWorkTypeSelectorSheet = true }) {
            Text("Select work type")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun BottomSheet() {
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
            WorkTypeChoiceView(WorkTypeChoiceViewModel(), hide).GetView(navController)
        }
    }
}