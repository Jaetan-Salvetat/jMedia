package fr.jaetan.jmedia.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.meetup.twain.MarkdownText
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.libraryComposable
import fr.jaetan.jmedia.app.search.searchComposable
import fr.jaetan.jmedia.app.settings.appearance.appearanceComposable
import fr.jaetan.jmedia.app.settings.settingsComposable
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalGithubReleaseManager
import fr.jaetan.jmedia.services.Navigator
import kotlinx.coroutines.launch

@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigator.library.route) {
        libraryComposable(navController)
        searchComposable(navController)
        settingsComposable(navController)
        appearanceComposable(navController)
    }

    UpdaterDialog()
}

@Composable
private fun UpdaterDialog() {
    var hasDownloadStarted by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val settings = LocalGithubReleaseManager.current
    val scope = rememberCoroutineScope()
    val downloadApk: () -> Unit = {
        scope.launch {
            hasDownloadStarted = true
            settings.download(context = context)
            hasDownloadStarted = false
            settings.removeRelease()
        }
    }

    settings.release?.let {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.SystemUpdate,
                    null,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = { Text(R.string.new_available_version.localized(), textAlign = TextAlign.Center) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(R.string.new_version_x.localized(it.tagName))
                    
                    MarkdownText(it.body, color = MaterialTheme.colorScheme.onBackground)

                    if (hasDownloadStarted) {
                        LinearProgressIndicator()
                    }
                }
            },
            onDismissRequest = { settings.removeRelease() },
            confirmButton = {
                TextButton(onClick = downloadApk) {
                    Text(R.string.download.localized())
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { settings.removeRelease() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(R.string.cancel.localized())
                }
            }
        )
    }
}