package fr.jaetan.jmedia.app.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jaetan.jmedia.app.settings.appearance.views.ContentView
import fr.jaetan.jmedia.app.settings.appearance.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class AppearanceView: Screen<AppearanceViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content(padding: PaddingValues) {
        Column(Modifier.padding(padding)) {
            ContentView()
        }
    }
}
