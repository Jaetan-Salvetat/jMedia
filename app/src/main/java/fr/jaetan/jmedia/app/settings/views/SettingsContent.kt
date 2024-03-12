package fr.jaetan.jmedia.app.settings.views

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.settings.SettingsView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.GlobalSettings
import fr.jaetan.jmedia.services.Navigator

@Composable
fun SettingsView.ContentView() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RowItem(R.string.appearance, R.string.appearance_subtitle) {
            navController?.navigate(Navigator.appearance.getNavRoute())
        }
        RowItem(R.string.show_onboarding, R.string.show_onboarding_subtitle) {}
        RowItem(R.string.contact_us, R.string.user_feedback) {}

        HorizontalDivider()

        RemoveDataCell()

        Spacer(Modifier.weight(1f))

        VersionCell()
    }
}

@Composable
private fun RowItem(
    @StringRes text: Int,
    @StringRes subtitle: Int,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
    ) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .padding(vertical = 10.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text(text.localized(), color = tint)
            Text(subtitle.localized(), fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
        }

        Icon(Icons.AutoMirrored.Default.ArrowForward, null)
    }
}

@Composable
private fun SettingsView.RemoveDataCell() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .border(1.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { viewModel.showRemoveDataDialog() }
            .padding(vertical = 15.dp, horizontal = 20.dp)

    ) {
        Text(R.string.remove_my_data.localized(), color = MaterialTheme.colorScheme.error)
        Text(R.string.remove_my_data_subtitle.localized(), fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun VersionCell() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = R.string.version_x_and_build_x.localized(GlobalSettings.fullVersionName, GlobalSettings.versionCode),
            color = MaterialTheme.colorScheme.outline,
            fontStyle = FontStyle.Italic
        )
    }
}
