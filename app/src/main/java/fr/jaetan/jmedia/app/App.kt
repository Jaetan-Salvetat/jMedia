package fr.jaetan.jmedia.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.services.Navigator

@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigator.library.route) {
        composable(Navigator.library.route) {
            LibraryView().GetView(navController, viewModel())
        }
        composable(
            route = Navigator.search.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    spring(.85f, Spring.StiffnessLow)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    spring(.85f, Spring.StiffnessLow)
                )
            }
        ) {
            SearchView().GetView(navController, viewModel())
        }
    }
}