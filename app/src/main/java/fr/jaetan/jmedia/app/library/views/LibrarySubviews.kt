package fr.jaetan.jmedia.app.library.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.core.services.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    TopAppBar(
        title = {},
        actions = {}
    )
}


@Composable
fun LibraryView.FabView() {
    FloatingActionButton(onClick = { navController?.navigate(Navigator.search.getNavRoute()) }) {
        Icon(Icons.Default.Add, null)
    }
}