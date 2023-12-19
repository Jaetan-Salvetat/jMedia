package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class SearchView: Screen<SearchViewModel>() {
    override val viewModel = SearchViewModel()

    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun Initialize(nc: NavHostController?) {
        super.Initialize(nc)
        viewModel.initializeObserver(LocalLifecycleOwner.current)
    }
}