package fr.jaetan.jmedia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

/**
 * Call GetView() for take a full view with a Scaffold, else call Content()
 */
@OptIn(ExperimentalMaterial3Api::class)
abstract class Screen <T: ViewModel> {
    private var isInitialized = false
    lateinit var viewModel: T
    /**
     * Default value is **TopAppBarDefaults.enterAlwaysScrollBehavior()**
     */
    lateinit var scrollBehavior: TopAppBarScrollBehavior
    var navController: NavHostController? = null
    open val useScrollBehavior = false

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content(padding: PaddingValues)

    @Composable
    open fun BottomBar() = Unit

    @Composable
    open fun GetView(nc: NavHostController?, viewModel: T) {
        if (!isInitialized) Initialize(nc, viewModel)

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
            Content(it)
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
        isInitialized = false
        this.viewModel = viewModel
        navController = nc
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    }
}