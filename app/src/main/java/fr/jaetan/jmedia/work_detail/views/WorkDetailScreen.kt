package fr.jaetan.jmedia.work_detail.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.jaetan.core.extensions.isNull
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.ui.widgets.ErrorDialogRetry
import fr.jaetan.jmedia.work_detail.WorkDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
class WorkDetailScreen(workType: WorkType, workName: String): Screen<WorkDetailViewModel>() {
    override val viewModel = WorkDetailViewModel(workType, workName)
    override val useDefaultPadding = false

    @Composable
    override fun TopBar() {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            },
            actions = {
                IconButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = { viewModel.likeHandler() }
                ) {
                    Icon(
                        imageVector = if (viewModel.isInLibrary) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = null,
                        tint = if (viewModel.isInLibrary) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }

    @Composable
    override fun Content() {
        ContentView()
    }

    @Composable
    override fun Dialogs() {
        ErrorDialogRetry(
            isVisible = viewModel.state == ListState.Error,
            dismiss = { navController?.popBackStack() },
            retry = { viewModel.fetchManga() }
        )
    }

    override fun initialize(nc: NavHostController?) {
        super.initialize(nc)

        if (viewModel.work.isNull()) {
            viewModel.fetchManga()
        }
    }
}