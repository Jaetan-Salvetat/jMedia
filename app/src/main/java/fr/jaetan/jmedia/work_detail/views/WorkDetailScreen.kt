package fr.jaetan.jmedia.work_detail.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.core.extensions.isNull
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.ui.widgets.ErrorDialogRetry
import fr.jaetan.jmedia.work_detail.WorkDetailViewModel

class WorkDetailScreen(workType: WorkType, workName: String): Screen<WorkDetailViewModel>() {
    override val viewModel = WorkDetailViewModel(workType, workName)
    override val useDefaultPadding = false

    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun Dialogs() {
        ErrorDialogRetry(
            isVisible = viewModel.state == ListState.Error,
            dismiss = { navController?.popBackStack() },
            retry = { viewModel.fetchManga() }
        )
    }

    override fun initialize(nc: NavHostController?) {
        super.initialize(nc)

        if (viewModel.work.isNull()) {
            viewModel.fetchManga()
        }
    }
}