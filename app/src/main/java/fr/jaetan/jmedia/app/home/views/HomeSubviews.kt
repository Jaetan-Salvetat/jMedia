package fr.jaetan.jmedia.app.home.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.app.home.HomeBottomBarItems
import fr.jaetan.jmedia.app.home.HomeView
import fr.jaetan.jmedia.app.home.localized
import fr.jaetan.jmedia.core.services.Navigator

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

        // To add a blank space
        NavigationBarItem(selected = false, onClick = {}, icon = {}, enabled = false)

        FloatingActionButton(
            onClick = { navController?.navigate(Navigator.search.getNavRoute()) },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 24.dp)
        ) {
            Icon(Icons.Rounded.TravelExplore, null)
        }
    }
}