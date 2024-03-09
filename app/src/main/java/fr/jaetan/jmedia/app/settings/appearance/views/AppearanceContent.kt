package fr.jaetan.jmedia.app.settings.appearance.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.app.settings.appearance.AppearanceView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.theme.themes.JTheme

@Composable
fun AppearanceView.ContentView() {
    ThemeSelectorCell()
}

@Composable
private fun AppearanceView.ThemeSelectorCell() {
    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(JTheme.entries) {
            if (it.shouldBeDisplayed) {
                ThemeCell(it)
            }
        }
    }
}

@Composable
private fun AppearanceView.ThemeCell(theme: JTheme) {
    val context = LocalContext.current
    val backgroundColor = if (MainViewModel.userSettings.currentTheme.name == theme.name) {
        MaterialTheme.colorScheme.scrim
    } else {
        Color.Transparent
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { viewModel.setTheme(context, theme) }
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Column {
            Row {
                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            theme.colorScheme.primaryContainer,
                            RoundedCornerShape(topStart = 8.dp)
                        )
                )
                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            theme.colorScheme.secondaryContainer,
                            RoundedCornerShape(topEnd = 8.dp)
                        )
                )
            }
            Row {

                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            theme.colorScheme.tertiaryContainer,
                            RoundedCornerShape(bottomStart = 8.dp)
                        )
                )
                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            theme.colorScheme.background,
                            RoundedCornerShape(bottomEnd = 8.dp)
                        )
                )
            }
        }

        Text(theme.title.localized())
    }
}