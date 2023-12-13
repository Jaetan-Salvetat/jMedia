package fr.jaetan.jmedia.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
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
            LibraryView().GetView(navController)
        }
        composable(
            route = Navigator.search.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    spring(.85f, 100f)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    spring(.85f, 100f)
                )
            }
        ) {
            SearchView().GetView(navController)
        }
    }
}