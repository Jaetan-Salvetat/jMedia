package fr.jaetan.jmedia.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
abstract class SubScreen <T: ViewModel> {
    private var isInitialized = false
    lateinit var viewModel: T
    /**
     * Default value is **TopAppBarDefaults.enterAlwaysScrollBehavior()**
     */
    lateinit var scrollBehavior: TopAppBarScrollBehavior
    var navController: NavHostController? = null

    @Composable
    open fun TopBar() = Unit

    @Composable
    abstract fun Content()

    @Composable
    open fun Initialize(nc: NavHostController?, model: T, scrollBehavior: TopAppBarScrollBehavior? = null) {
        isInitialized = false
        viewModel = model
        navController = nc
        scrollBehavior?.let { this.scrollBehavior = it }
    }
}