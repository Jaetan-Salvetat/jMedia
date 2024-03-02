package fr.jaetan.jmedia.app.settings.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.extensions.toPainter
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.services.GlobalSettings

@Composable
fun SettingsView.ContentView() {
    LazyColumn {
        item { RowItem(R.string.appearance, leadingIconDrawable = R.drawable.ic_appearance) }

        item { VersionCell() }
    }
}

@Composable
private fun RowItem(@StringRes text: Int, leadingIconVector: ImageVector? = null, @DrawableRes leadingIconDrawable: Int? = null) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp)
        .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIconVector?.let {
            Icon(it, null)
        }
        leadingIconDrawable?.let {
            Icon(it.toPainter(), null)
        }

        Text(
            text.localized(),
            fontSize = 13.sp
        )
    }
}

@Composable
fun VersionCell() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = R.string.version_x_build_x.localized(GlobalSettings.versionName, GlobalSettings.versionCode)
        )
    }
}