package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.jmedia.app.library.views.BottomSheetView
import fr.jaetan.jmedia.app.library.views.FabView
import fr.jaetan.jmedia.app.library.views.TopBarView
import fr.jaetan.jmedia.core.extensions.isNull
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.ui.Screen

class LibraryView: Screen<LibraryViewModel>() {
    override val viewModel = LibraryViewModel()

    @Composable
    override fun TopBar() {
        TopBarView()
    }

    @Composable
    override fun Content() = Unit

    @Composable
    override fun BottomSheet() {
        BottomSheetView()
    }

    @Composable
    override fun Fab() {
        FabView()
    }

    @Composable
    override fun Initialize(nc: NavHostController?) {
        super.Initialize(nc)

        if (MainViewModel.state.currentWorkType.isNull()) {
            viewModel.showWorkTypeSelectorSheet = true
        }
    }
}