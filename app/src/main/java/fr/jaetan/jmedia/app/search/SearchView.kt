package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class SearchView: Screen<SearchViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }
}