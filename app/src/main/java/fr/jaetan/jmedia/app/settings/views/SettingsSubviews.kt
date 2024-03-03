package fr.jaetan.jmedia.app.settings.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.extensions.localized

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView.TopBarView() {
    TopAppBar(
        title = { Text(R.string.app_settings.localized()) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun SettingsView.RemoveDataDialog() {
    if (viewModel.showRemoveDataDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showRemoveDataDialog = false },
            title = { Text(R.string.remove_my_data.localized()) },
            text = {
                   Column {
                       Text(R.string.remove_data_dialog_content_1.localized())
                       Text(R.string.remove_data_dialog_content_2.localized())
                   }
            },
            confirmButton = {
                TextButton(
                    onClick = viewModel::removeData,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(R.string.confirm.localized())
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showRemoveDataDialog = false }) {
                    Text(R.string.cancel.localized())
                }
            }
        )
    }
}