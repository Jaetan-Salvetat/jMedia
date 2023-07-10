package fr.jaetan.jmedia.home.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.home.HomeViewModel
import fr.jaetan.jmedia.ui.widgets.JScaledContent

@OptIn(ExperimentalMaterial3Api::class)
class HomeScreen: Screen<HomeViewModel>() {
    override val viewModel = HomeViewModel()

    @Composable
    override fun TopBar() {
        TopAppBar(
            title = {},
            actions = {
                JScaledContent(Modifier.padding(10.dp), onPressed = { viewModel.showBottomSheet = true }) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
    
    @Composable
    override fun Content() {
        HomeViewContent()
    }

    @Composable
    override fun BottomSheet() {
        BottomSheetView()
    }
}