package fr.jaetan.jmedia.library.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import fr.jaetan.core.models.data.works.WorkType

@Composable
fun LibraryScreen.WorksMenu() {
    DropdownMenu(expanded = viewModel.showWorkMenu, onDismissRequest = { viewModel.showWorkMenu = false }) {
        val selectedColor = MaterialTheme.colorScheme.tertiary
        val unselectedColor = MaterialTheme.colorScheme.onBackground

        WorkType.all.forEach {
            val selectedBackground by animateColorAsState(
                targetValue =  if (it == viewModel.workType) {
                    selectedColor.copy(alpha = .3f)
                } else {
                    Color.Transparent
                },
                label = ""
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(it.textRes),
                        color = if (it == viewModel.workType) selectedColor else unselectedColor
                    )
                },
                onClick = { viewModel.toggleWorkType(it) },
                modifier = Modifier.background(selectedBackground)
            )
        }
    }
}