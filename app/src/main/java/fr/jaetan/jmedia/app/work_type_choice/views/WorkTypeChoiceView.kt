package fr.jaetan.jmedia.app.work_type_choice.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceViewModel
import fr.jaetan.jmedia.ui.Screen
import fr.jaetan.jmedia.ui.shared.ErrorSheet
import fr.jaetan.jmedia.R
import kotlinx.coroutines.launch

class WorkTypeChoiceView(val hide: suspend () -> Unit): Screen<WorkTypeChoiceViewModel>() {
    override val viewModel: WorkTypeChoiceViewModel = WorkTypeChoiceViewModel(hide)

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

    @Composable
    override fun Initialize(nc: NavHostController?) {
        super.Initialize(nc)
    }
}