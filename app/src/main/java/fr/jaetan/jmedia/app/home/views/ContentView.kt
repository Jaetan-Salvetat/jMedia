package fr.jaetan.jmedia.app.home.views

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.home.HomeBottomBarItems
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.app.settings.SettingsView

@Composable
fun HomeView.ContentView() {
    when (viewModel.currentScreen) {
        HomeBottomBarItems.Library -> LibraryView().Content()
        HomeBottomBarItems.Search -> SearchView().Content()
        HomeBottomBarItems.Settings -> SettingsView().Content()
    }
}
