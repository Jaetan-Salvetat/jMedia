package fr.jaetan.jmedia.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.app.work_detail.views.WorkDetailView
import fr.jaetan.jmedia.app.work_detail.views.WorkDetailViewModel
import fr.jaetan.jmedia.core.services.Analytics
import fr.jaetan.jmedia.core.services.Navigator
import fr.jaetan.jmedia.models.WorkType
import org.mongodb.kbson.ObjectId

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

        composable(Navigator.workDetail.route) {

            val workType = it.arguments?.getString(Navigator.workDetail.workType) ?: ""
            val workId = it.arguments?.getString(Navigator.workDetail.workId) ?: ""

            WorkDetailView().GetView(navController, WorkDetailViewModel(WorkType.fromString(workType), ObjectId(workId)))
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