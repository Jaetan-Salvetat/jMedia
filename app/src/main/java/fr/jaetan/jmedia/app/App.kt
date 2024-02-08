package fr.jaetan.jmedia.app

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.core.services.Analytics
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun App(navController: NavHostController) {
    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        navDestination.route?.let {
            Analytics.tagScreen(it)
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        MainViewModel.mangaRepository.all.collect {
            Log.d("testt", "App")
        }
    }

    NavHost(navController = navController, startDestination = Navigator.home.route) {
        composable(Navigator.home.route) {
            HomeView().GetView(navController, viewModel())
        }
        /*composable(
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
        }*/
    }
}