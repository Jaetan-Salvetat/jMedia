package fr.jaetan.core.models.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
abstract class Screen <T: ViewModel> {
    abstract val viewModel: T
    var navController: NavHostController? = null
    lateinit var scrollBehavior: TopAppBarScrollBehavior

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content()

    @Composable
    open fun BottomBar() = Unit

    @Composable
    fun GetView(nc: NavHostController? = null) {
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        navController = nc

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab() },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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