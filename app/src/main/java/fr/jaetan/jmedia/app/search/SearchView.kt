package fr.jaetan.jmedia.app.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.ui.SubScreen
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
class SearchView(val searchValue: StateFlow<String>): SubScreen<SearchViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun Initialize(
        nc: NavHostController?,
        model: SearchViewModel,
        scrollBehavior: TopAppBarScrollBehavior?
    ) {
        super.Initialize(nc, model, scrollBehavior)
        viewModel.searchValue = searchValue.collectAsState().value
    }
}