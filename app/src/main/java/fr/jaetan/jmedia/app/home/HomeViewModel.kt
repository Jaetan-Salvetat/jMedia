package fr.jaetan.jmedia.app.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.ui.Screen

class HomeViewModel: ViewModel() {
    var currentScreen by mutableStateOf(HomeBottomBarItems.Library)
}

enum class HomeBottomBarItems(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val view: Screen<*>
    ) {
    Library(R.string.library, Icons.Outlined.LibraryBooks, Icons.Filled.LibraryBooks, LibraryView()),
    Search(R.string.research, Icons.Outlined.TravelExplore, Icons.Filled.TravelExplore, SearchView()),
    Settings(R.string.settings, Icons.Outlined.Settings, Icons.Filled.Settings, SettingsView())
}