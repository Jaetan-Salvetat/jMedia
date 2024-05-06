package fr.jaetan.jmedia.app.library

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.services.Navigator
import fr.jaetan.jmedia.ui.Screen

class LibraryView : Screen<LibraryViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content(padding: PaddingValues) = Unit
}

fun NavGraphBuilder.libraryComposable(navController: NavHostController) {
    composable(
        route = Navigator.library.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut(tween(200)) }
    ) {
        LibraryView().GetView(navController, viewModel())
    }
}