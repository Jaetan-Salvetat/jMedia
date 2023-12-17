package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.views.FabView
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class LibraryView: Screen<LibraryViewModel>() {
    override val viewModel = LibraryViewModel()

    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() = Unit

    @Composable
    override fun Fab() {
        FabView()
    }
}