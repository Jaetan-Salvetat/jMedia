package fr.jaetan.jmedia.app.work_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.work_detail.views.ContentView
import fr.jaetan.jmedia.app.work_detail.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class WorkDetailView: Screen<WorkDetailViewModel>() {

    @Composable
    override fun Content(padding: PaddingValues) {
        ContentView()
    }

    @Composable
    override fun TopBar() {
        TopBarView()
    }

}