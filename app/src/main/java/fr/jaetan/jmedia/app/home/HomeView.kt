package fr.jaetan.jmedia.app.home

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.home.views.BottomBarView
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
class HomeView : Screen<HomeViewModel>() {
    private val searchView by lazy { SearchView(viewModel.searchValue) }
    private val settingsView by lazy { SettingsView() }
    private val libraryView by lazy {
        LibraryView(viewModel.searchValue) {
            it?.let {
                viewModel.searchValue.value = it
            }
            viewModel.currentScreen = HomeBottomBarItems.Search
        }
    }

    override val useScrollBehavior = true

    @SuppressLint("UnusedContentLambdaTargetStateParameter")
    @Composable
    override fun Content(padding: PaddingValues) {
        Crossfade(
            targetState = viewModel.currentScreen,
            label = ""
        ) {
            Column(Modifier.padding(bottom = padding.calculateBottomPadding())) {
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
    override fun BottomSheet() {
        when (viewModel.currentScreen) {
            HomeBottomBarItems.Library -> libraryView.BottomSheet()
            HomeBottomBarItems.Search -> searchView.BottomSheet()
            HomeBottomBarItems.Settings -> settingsView.BottomSheet()
        }
    }

    @Composable
    override fun Dialogs() {
        when (viewModel.currentScreen) {
            HomeBottomBarItems.Library -> libraryView.Dialogs()
            HomeBottomBarItems.Search -> searchView.Dialogs()
            HomeBottomBarItems.Settings -> settingsView.Dialogs()
        }
    }

    @Composable
    override fun Initialize(nc: NavHostController?, viewModel: HomeViewModel) {
        super.Initialize(nc, viewModel)

        libraryView.Initialize(navController, viewModel(), scrollBehavior)
        searchView.Initialize(navController, viewModel(), scrollBehavior)
        settingsView.Initialize(navController, viewModel(), scrollBehavior)
    }
}