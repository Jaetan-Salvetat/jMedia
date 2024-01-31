package fr.jaetan.jmedia.app.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.jaetan.jmedia.app.home.views.BottomBarView
import fr.jaetan.jmedia.app.home.views.ContentView
import fr.jaetan.jmedia.ui.Screen

class HomeView: Screen<HomeViewModel>() {
    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun BottomBar() {
        BottomBarView()
    }
}

@Composable
fun Int.localized(): String = stringResource(this)