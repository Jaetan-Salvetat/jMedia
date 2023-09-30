package fr.jaetan.jmedia.app.work_type_choice.views

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceViewModel
import fr.jaetan.jmedia.ui.Screen
import fr.jaetan.jmedia.ui.shared.ErrorSheet
import fr.jaetan.jmedia.R

class WorkTypeChoiceView(
    override val viewModel: WorkTypeChoiceViewModel,
    val hide: suspend () -> Unit
): Screen<WorkTypeChoiceViewModel>() {
    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun BottomSheet() {
        ErrorSheet(message = R.string.feature_not_available, isVisible = viewModel.showErrorSheet) {
            viewModel.showErrorSheet = false
        }
    }
}