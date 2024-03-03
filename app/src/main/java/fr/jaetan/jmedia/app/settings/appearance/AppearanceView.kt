package fr.jaetan.jmedia.app.settings.appearance

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.settings.appearance.views.TopBarView
import fr.jaetan.jmedia.ui.Screen

class AppearanceView: Screen<AppearanceViewModel>() {
    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content(padding: PaddingValues) = Unit
}
