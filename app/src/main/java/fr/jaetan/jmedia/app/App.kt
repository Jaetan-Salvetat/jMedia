package fr.jaetan.jmedia.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.meetup.twain.MarkdownText
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.app.settings.appearance.AppearanceView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.LocalGithubReleaseManager
import fr.jaetan.jmedia.services.Navigator
import kotlinx.coroutines.launch

@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigator.home.route) {
        composable(Navigator.home.route) {
            HomeView().GetView(navController, viewModel())
        }
        composable(
            route = Navigator.appearance.route,
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
            AppearanceView().GetView(navController, viewModel())
        }
    }

    UpdaterDialog()
}

@Composable
private fun UpdaterDialog() {
    var hasDownloadStarted by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val settings = LocalGithubReleaseManager.current
    val scope = rememberCoroutineScope()
    val downloadApk: () -> Unit = {
        scope.launch {
            hasDownloadStarted = true
            settings.download(context = context)
            hasDownloadStarted = false
            settings.removeRelease()
        }
    }

    settings.release?.let {
        AlertDialog(
            icon = { Icon(Icons.Default.FiberNew, null) },
            title = { Text(R.string.version_x.localized(it.tagName)) },
            text = {
                Column {
                    MarkdownText(it.body, color = MaterialTheme.colorScheme.onBackground)

                    if (hasDownloadStarted) {
                        LinearProgressIndicator(modifier = Modifier.padding(top = 10.dp))
                    }
                }
            },
            onDismissRequest = { settings.removeRelease() },
            confirmButton = {
                TextButton(onClick = downloadApk) {
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