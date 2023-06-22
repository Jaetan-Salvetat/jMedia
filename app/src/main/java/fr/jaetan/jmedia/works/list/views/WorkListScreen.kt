package fr.jaetan.jmedia.works.list.views

import androidx.compose.runtime.Composable
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.works.list.WorkListViewModel

class WorkListScreen: Screen<WorkListViewModel>() {
    override val viewModel = WorkListViewModel()

    @Composable
    override fun Content() {

    }
}