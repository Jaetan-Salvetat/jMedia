@file:Suppress("ktlint:standard:no-unused-imports")

package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.services.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView.TopBarView() {
    val mediasManager = LocalMediaManager.current

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp), CircleShape)
                    .clip(CircleShape)
                    .clickable { navController?.navigate(Navigator.search.route) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Search, null)
                Text(
                    text = R.string.search_library_placeholder.localized(mediasManager.count),
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        actions = {
            IconButton(onClick = { navController?.navigate(Navigator.settings.getNavRoute()) }) {
                Icon(Icons.Rounded.Settings, null)
            }
        }
    )
}