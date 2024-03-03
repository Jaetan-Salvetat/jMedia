package fr.jaetan.jmedia.app.settings.appearance.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.settings.appearance.AppearanceView
import fr.jaetan.jmedia.extensions.localized

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceView.TopBarView() {
    TopAppBar(
        title = { Text(R.string.appearance.localized()) },
        navigationIcon = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, null)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}