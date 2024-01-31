package fr.jaetan.jmedia.app.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.home.localized
import fr.jaetan.jmedia.ui.Screen

class SettingsView: Screen<SettingsViewModel>() {
    @Composable
    override fun Content() {
        Text(R.string.settings.localized())
    }
}