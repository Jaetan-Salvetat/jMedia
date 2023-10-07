package fr.jaetan.jmedia.app.search.views

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.search.SearchViewModel
import fr.jaetan.jmedia.ui.Screen

class SearchView: Screen<SearchViewModel>() {
    override val viewModel = SearchViewModel()

    @Composable
    override fun Content() {
        ContentView()
    }
}