package fr.jaetan.jmedia.ui

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
    lateinit var viewModel: T
    lateinit var scrollBehavior: TopAppBarScrollBehavior

    open val useScrollBehavior = false
    var navController: NavHostController? = null

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content()

    @Composable
    open fun BottomBar() = Unit

    @Composable
    open fun GetView(nc: NavHostController? = null, viewModel: T) {
        Initialize(nc, viewModel)

        val modifier = if (useScrollBehavior) {
            Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        } else {
            Modifier
        }

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            floatingActionButton = { Fab() },
            modifier = modifier
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

    @Composable
    open fun Initialize(nc: NavHostController?, viewModel: T) {
        this.viewModel = viewModel
        navController = nc
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    }
}