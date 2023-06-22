package fr.jaetan.jmedia.library.views

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.library.LibraryNavigator
import fr.jaetan.jmedia.library.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
class LibraryScreen(private val activity: Activity, workType: WorkType): Screen<LibraryViewModel>() {
    override val viewModel = LibraryViewModel(workType)

    @Composable
    override fun TopBar() {
        TopAppBar(
            title = { Text(stringResource(viewModel.workType.titleRes)) },
            navigationIcon = {
                IconButton(onClick = { activity.finish() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            },
            actions = {
                Column {
                    IconButton(onClick = { viewModel.showWorkMenu = true }) {
                        Icon(Icons.Default.SwapHoriz, null)
                    }
                    WorksMenu()
                }
            }
        )

    }

    @Composable
    override fun Content() {

    }

    @Composable
    override fun Fab() {
        val context = LocalContext.current

        FloatingActionButton(
            onClick = { LibraryNavigator.search.openActivity(context, viewModel.workType) }
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}