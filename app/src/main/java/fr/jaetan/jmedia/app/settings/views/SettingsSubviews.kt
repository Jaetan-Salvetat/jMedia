package fr.jaetan.jmedia.app.settings.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalMediaManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView.TopBarView() {
    TopAppBar(
        title = { Text(R.string.settings.localized()) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.Default.Clear, null)
            }
        }
    )
}

@Composable
fun SettingsView.RemoveDataDialog() {
    val mediasManager = LocalMediaManager.current
    val context = LocalContext.current

    if (viewModel.showRemoveDataDialog) {
        AlertDialog(
            onDismissRequest = viewModel::hideRemoveDataDialog,
            title = { Text(R.string.remove_my_data.localized()) },
            text = {
                Column {
                    Text(R.string.remove_data_dialog_content_1.localized())
                    Text(R.string.remove_data_dialog_content_2.localized())
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.removeData(context, mediasManager) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(R.string.confirm.localized())
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::hideRemoveDataDialog, enabled = !viewModel.isLoading) {
                    Text(R.string.cancel.localized())
                }
            }
        )
    }
}