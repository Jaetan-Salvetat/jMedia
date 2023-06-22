package fr.jaetan.jmedia.works.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.works.WorkDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
class WorkDetailScreen(workName: String): Screen<WorkDetailViewModel>() {
    override val viewModel = WorkDetailViewModel(workName)

    @Composable
    override fun TopBar() {
        LargeTopAppBar(
            title = {
                Text(viewModel.workName)
            },
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                /*IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }*/
            }
        )
    }

    @Composable
    override fun Content() {

    }
}