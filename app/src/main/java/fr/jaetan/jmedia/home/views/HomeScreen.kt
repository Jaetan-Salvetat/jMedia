package fr.jaetan.jmedia.home.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
class HomeScreen: Screen<HomeViewModel>() {
    override val viewModel = HomeViewModel()

    @Composable
    override fun TopBar() {
        TopAppBar(
            title = {},
            actions = {
                IconButton(onClick = { viewModel.showBottomSheet = true }) {
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