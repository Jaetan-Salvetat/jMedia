package fr.jaetan.jmedia.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
abstract class Screen <T: ViewModel> {
    abstract val viewModel: T
    open val useDefaultPadding = true
    var navController: NavHostController? = null

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content()

    @Composable
    open fun BottomBar() = Unit

    @Composable
    open fun GetView(nc: NavHostController? = null) {
        Initialize(nc)

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab() }
        ) {
            Box(Modifier.padding(if (useDefaultPadding) it else PaddingValues(0.dp))) { Content() }
        }

        Dialogs()
        BottomSheet()
    }

    @Composable
    open fun Dialogs() = Unit

    @Composable
    open fun Fab() = Unit

    @Composable
    open fun BottomSheet() = Unit

    @Composable
    open fun Initialize(nc: NavHostController?) {
        navController = nc
    }
}