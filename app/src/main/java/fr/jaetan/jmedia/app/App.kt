package fr.jaetan.jmedia.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.library.views.LibraryView
import fr.jaetan.jmedia.core.services.Navigator
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigator.library.route) {
        composable(Navigator.library.route) {
            LibraryView().GetView()
        }
    }
}