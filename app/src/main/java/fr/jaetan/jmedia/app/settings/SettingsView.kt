package fr.jaetan.jmedia.app.settings

import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.settings.views.ContentView
import fr.jaetan.jmedia.app.settings.views.TopBarView
import fr.jaetan.jmedia.ui.SubScreen

class SettingsView: SubScreen<SettingsViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() {
        ContentView()
    }
}