package fr.jaetan.jmedia.app.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.search.views.ContentView
import fr.jaetan.jmedia.app.search.views.TopBarView
import fr.jaetan.jmedia.services.Navigator
import fr.jaetan.jmedia.services.slideIntoVerticalContainer
import fr.jaetan.jmedia.services.slideOutVerticalContainer
import fr.jaetan.jmedia.ui.Screen

class SearchView : Screen<SearchViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content(padding: PaddingValues) {
        Box(Modifier.padding(padding)) {
            ContentView()
        }
    }
}

fun NavGraphBuilder.searchComposable(navController: NavHostController) {
    composable(
        Navigator.search.route,
        enterTransition = { slideIntoVerticalContainer() },
        exitTransition = { slideOutVerticalContainer() }
    ) {
        SearchView().GetView(navController, viewModel())
    }
}