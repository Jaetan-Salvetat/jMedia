package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.views.ContentView
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.ui.SubScreen

class LibraryView: SubScreen<LibraryViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }
}