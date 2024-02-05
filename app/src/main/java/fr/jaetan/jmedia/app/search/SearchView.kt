package fr.jaetan.jmedia.app.search

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.ui.SubScreen

@OptIn(ExperimentalMaterial3Api::class)
class SearchView: SubScreen<SearchViewModel>() {
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

    @Composable
    override fun Initialize(
        nc: NavHostController?,
        model: SearchViewModel,
        scrollBehavior: TopAppBarScrollBehavior?
    ) {
        super.Initialize(nc, model, scrollBehavior)

        BackHandler {
            popBackStack()
        }
    }
}