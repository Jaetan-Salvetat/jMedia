package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.ui.SubScreen
import kotlinx.coroutines.flow.StateFlow

class LibraryView(val searchValue: StateFlow<String>, val navigateToSearchBab: (String?) -> Unit) : SubScreen<LibraryViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() = Unit
}