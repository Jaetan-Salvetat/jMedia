package fr.jaetan.jmedia.app.search.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jaetan.jmedia.app.search.SearchViewModel
import fr.jaetan.jmedia.ui.Screen

class SearchView(private val searchValue: String): Screen<SearchViewModel>() {
    override val viewModel = SearchViewModel()

    @Composable
    override fun Content() {
        ContentView()
    }
}