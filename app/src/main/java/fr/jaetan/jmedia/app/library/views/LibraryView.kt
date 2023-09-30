package fr.jaetan.jmedia.app.library.views

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.LibraryViewModel
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceViewModel
import fr.jaetan.jmedia.app.work_type_choice.views.WorkTypeChoiceView
import fr.jaetan.jmedia.ui.Screen
import fr.jaetan.jmedia.ui.widgets.JBottomSheet

class LibraryView: Screen<LibraryViewModel>() {
    override val viewModel = LibraryViewModel()

    @Composable
    override fun Content() {
        Button(onClick = { viewModel.showWorkTypeSelectorSheet = true }) {
            Text("Select work type")
        }
    }

    @Composable
    override fun BottomSheet() {
        JBottomSheet(
            isVisible = viewModel.showWorkTypeSelectorSheet,
            dismiss = { viewModel.showWorkTypeSelectorSheet = false },
            shouldBeFullScreen = true
        ) {
            WorkTypeChoiceView(WorkTypeChoiceViewModel()).GetView()
        }
    }
}