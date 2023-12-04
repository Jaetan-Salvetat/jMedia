package fr.jaetan.jmedia.app.work_type_choice

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.work_type_choice.views.ContentView
import fr.jaetan.jmedia.app.work_type_choice.views.TopBarView
import fr.jaetan.jmedia.ui.Screen
import fr.jaetan.jmedia.ui.shared.ErrorSheet

class WorkTypeChoiceView(val hide: suspend () -> Unit): Screen<WorkTypeChoiceViewModel>() {
    override val viewModel = WorkTypeChoiceViewModel(hide)

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