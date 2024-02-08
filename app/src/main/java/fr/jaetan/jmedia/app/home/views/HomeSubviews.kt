package fr.jaetan.jmedia.app.home.views

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.home.HomeBottomBarItems
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.extensions.localized

@Composable
fun HomeView.BottomBarView() {
    NavigationBar {
        HomeBottomBarItems.entries.forEach {
            val isSelected = it == viewModel.currentScreen

            NavigationBarItem(
                selected = isSelected,
                alwaysShowLabel = false,
                onClick = { viewModel.currentScreen = it },
                icon = { Icon(if (isSelected) it.selectedIcon else it.icon, null) },
                label = { Text(it.titleRes.localized()) },
            )
        }
    }
}