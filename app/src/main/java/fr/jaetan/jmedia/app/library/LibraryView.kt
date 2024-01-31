package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.views.ContentView
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class LibraryView: Screen<LibraryViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }
}