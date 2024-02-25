package fr.jaetan.jmedia.app

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.meetup.twain.MarkdownText
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.core.networking.LocalReleaseSettings
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.Analytics
import fr.jaetan.jmedia.services.Navigator

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

    UpdaterDialog()
}

@Composable
private fun UpdaterDialog() {
    val uriHandler = LocalUriHandler.current
    val settings = LocalReleaseSettings.current

    settings.release?.let {
        AlertDialog(
            title = { Text(R.string.version_x.localized(it.tagName)) },
            text = {
                MarkdownText(it.body, color = MaterialTheme.colorScheme.onBackground)
            },
            onDismissRequest = { settings.removeRelease() },
            confirmButton = {
                TextButton(onClick = { uriHandler.openUri(it.assets.first().browserDownloadUrl) }) {
                    Text(R.string.download.localized())
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { settings.removeRelease() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(R.string.cancel.localized())
                }
            }
        )
    }
}