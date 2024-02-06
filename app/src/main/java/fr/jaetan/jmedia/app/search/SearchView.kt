package fr.jaetan.jmedia.app.search

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.ui.Screen

class SearchView: Screen<SearchViewModel>() {
    override val useScrollBehavior = true

    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }

    fun popBackStack() {
        navController?.popBackStack()

        MainViewModel.controllers.forEach {
            it.resetWorks()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Initialize(nc: NavHostController?, viewModel: SearchViewModel) {
        super.Initialize(nc, viewModel)

        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        BackHandler {
            popBackStack()
        }
    }
}