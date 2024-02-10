package fr.jaetan.jmedia.app.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.ui.SubScreen

class SettingsView: SubScreen<SettingsViewModel>() {
    @Composable
    override fun Content() {
        Text(R.string.settings.localized())
    }
}