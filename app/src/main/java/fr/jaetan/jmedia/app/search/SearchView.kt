package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.core.services.MainViewModel
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

    @Composable
    override fun Initialize(nc: NavHostController?, model: SearchViewModel) {
        super.Initialize(nc, model)

        MainViewModel.controllers.forEach {
            it.resetWorks()
        }
    }
}