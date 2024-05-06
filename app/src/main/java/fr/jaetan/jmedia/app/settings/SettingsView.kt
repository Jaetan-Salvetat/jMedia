package fr.jaetan.jmedia.app.settings

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.jaetan.jmedia.app.settings.views.ContentView
import fr.jaetan.jmedia.app.settings.views.RemoveDataDialog
import fr.jaetan.jmedia.app.settings.views.TopBarView
import fr.jaetan.jmedia.services.Navigator
import fr.jaetan.jmedia.services.slideIntoVerticalContainer
import fr.jaetan.jmedia.services.slideOutVerticalContainer
import fr.jaetan.jmedia.ui.Screen

class SettingsView : Screen<SettingsViewModel>() {
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

    @Composable
    override fun Dialogs() {
        RemoveDataDialog()
    }
}

fun NavGraphBuilder.settingsComposable(navController: NavHostController) {
    composable(
        Navigator.settings.route,
        enterTransition = {
            when (initialState.destination.route) {
                "appearance" -> fadeIn()
                else -> slideIntoVerticalContainer()
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                "appearance" -> fadeOut()
                else -> slideOutVerticalContainer()
            }
        }
    ) {
        SettingsView().GetView(navController, viewModel())
    }
}