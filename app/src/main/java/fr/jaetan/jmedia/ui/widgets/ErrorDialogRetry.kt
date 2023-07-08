package fr.jaetan.jmedia.ui.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import fr.jaetan.jmedia.R

@Composable
fun ErrorDialogRetry(isVisible: Boolean, dismiss: () -> Unit, retry: () -> Unit) {
    if (!isVisible) return

    AlertDialog(
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(onClick = retry) {
                Text(stringResource(R.string.retry))
            }
        },
        dismissButton = {
            TextButton(onClick = dismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        title =  {
            Text(stringResource(R.string.error_upper_case), color = MaterialTheme.colorScheme.error)
        },
        text = {
            Text(stringResource(R.string.error_text), fontStyle = FontStyle.Italic)
        }
    )
}