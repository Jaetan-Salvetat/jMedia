package fr.jaetan.jmedia.app.home

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.home.views.BottomBarView
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
class HomeView: Screen<HomeViewModel>() {
    private val libraryView = LibraryView()
    private val searchView = SearchView()
    private val settingsView = SettingsView()

    @SuppressLint("UnusedContentLambdaTargetStateParameter")
    @Composable
    override fun Content(padding: PaddingValues) {
        Crossfade(
            targetState = viewModel.currentScreen,
            label = ""
        ) {
            Column {
                when (it) {
                    HomeBottomBarItems.Library -> libraryView.TopBar()
                    HomeBottomBarItems.Search -> searchView.TopBar()
                    HomeBottomBarItems.Settings -> settingsView.TopBar()
                }

                when (it) {
                    HomeBottomBarItems.Library -> libraryView.Content()
                    HomeBottomBarItems.Search -> searchView.Content()
                    HomeBottomBarItems.Settings -> settingsView.Content()
                }
            }
        }
    }

    @Composable
    override fun BottomBar() {
        BottomBarView()
    }

    @Composable
    override fun Initialize(nc: NavHostController?, model: HomeViewModel) {
        super.Initialize(nc, model)

        libraryView.Initialize(navController, viewModel(), scrollBehavior)
        searchView.Initialize(navController, viewModel(), scrollBehavior)
        settingsView.Initialize(navController, viewModel(), scrollBehavior)
    }
}

@Composable
fun Int.localized(): String = stringResource(this)