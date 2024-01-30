package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.library.views.ContentView
import fr.jaetan.jmedia.app.library.views.FabView
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class LibraryView: Screen<LibraryViewModel>() {
    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun Fab() {
        FabView()
    }

    @Composable
    override fun Initialize(nc: NavHostController?, viewModel: LibraryViewModel) {
        super.Initialize(nc, viewModel)
    }
}