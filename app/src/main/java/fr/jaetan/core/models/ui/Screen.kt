package fr.jaetan.core.models.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

abstract class Screen <T: ViewModel> {
    abstract val viewModel: T
    var navController: NavHostController? = null

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content()

    @Composable
    open fun BottomBar() = Unit

    @Composable
    fun GetView(nc: NavHostController? = null) {
        navController = nc

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab() }
        ) {
            Box(Modifier.padding(it)) { Content() }
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
}