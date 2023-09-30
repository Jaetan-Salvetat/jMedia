package fr.jaetan.jmedia.app.work_type_choice.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTypeChoiceView.TopBarView() {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = {
                scope.launch {
                    hide()
                }
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    )
}