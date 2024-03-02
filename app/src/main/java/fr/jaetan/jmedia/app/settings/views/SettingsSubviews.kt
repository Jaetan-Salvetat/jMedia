package fr.jaetan.jmedia.app.settings.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.extensions.localized

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView.TopBarView() {
    TopAppBar(
        title = { Text(R.string.settings.localized()) },
        scrollBehavior = scrollBehavior
    )
}