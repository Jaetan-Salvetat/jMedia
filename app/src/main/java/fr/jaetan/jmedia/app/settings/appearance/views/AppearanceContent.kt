package fr.jaetan.jmedia.app.settings.appearance.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.settings.appearance.AppearanceView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.models.JColorScheme
import fr.jaetan.jmedia.models.JTheme
import fr.jaetan.jmedia.services.MainViewModel

@Composable
fun AppearanceView.ContentView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item { ColorSchemeSelector() }

        item { HorizontalDivider(Modifier.padding(horizontal = 20.dp)) }

        item { ThemeSwitcher() }
        item { PureDarkSelector() }
    }
}

@Composable
private fun AppearanceView.ColorSchemeSelector() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(R.string.color_scheme.localized(), modifier = Modifier.padding(start = 20.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            items(JColorScheme.entries) {
                if (it.shouldBeDisplayed) {
                    ThemeCell(it)
                }
            }
        }
    }
}

@Composable
private fun AppearanceView.ThemeCell(theme: JColorScheme) {
    val context = LocalContext.current
    val backgroundColor = if (MainViewModel.userSettings.currentColorScheme.name == theme.name) {
        MaterialTheme.colorScheme.scrim
    } else {
        Color.Transparent
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { viewModel.setColorScheme(context, theme) }
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

@Composable
private fun AppearanceView.ThemeSwitcher() {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.showThemeSelectorMenu = true }
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(R.string.theme.localized())
                Text(
                    text = R.string.follow_theme.localized(),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Column {
                TextButton(onClick = { viewModel.showThemeSelectorMenu = true }) {
                    Text(MainViewModel.userSettings.currentTheme.title.localized())
                }

                DropdownMenu(
                    expanded = viewModel.showThemeSelectorMenu,
                    onDismissRequest = { viewModel.showThemeSelectorMenu = false }
                ) {
                    JTheme.entries.forEach {
                        DropdownMenuItem(
                            text = { Text(it.title.localized()) },
                            leadingIcon = { Icon(it.icon, null) },
                            onClick = { viewModel.setTheme(context, it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppearanceView.PureDarkSelector() {
    val context = LocalContext.current
    val isDarkTheme = viewModel.isDarkTheme(isSystemInDarkTheme())

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(isDarkTheme) { viewModel.setPurDark(context, !MainViewModel.userSettings.isPureDark) }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column {
            Text(R.string.pur_dark.localized())
            Text(
                text = R.string.pur_dark_subtitle.localized(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }

        Switch(
            checked = if (isDarkTheme) MainViewModel.userSettings.isPureDark else false,
            enabled = isDarkTheme,
            onCheckedChange = { viewModel.setPurDark(context, it) }
        )
    }
}