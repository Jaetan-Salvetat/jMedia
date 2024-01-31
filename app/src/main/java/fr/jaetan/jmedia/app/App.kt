package fr.jaetan.jmedia.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.services.Analytics
import fr.jaetan.jmedia.core.services.Navigator

@Composable
fun App(navController: NavHostController) {
    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        navDestination.route?.let {
            Analytics.tagScreen(it)
        }
    }

    NavHost(navController = navController, startDestination = Navigator.home.route) {
        composable(Navigator.home.route) {
            HomeView().GetView(navController, viewModel())
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