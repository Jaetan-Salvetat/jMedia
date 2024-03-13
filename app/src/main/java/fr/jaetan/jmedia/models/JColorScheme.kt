package fr.jaetan.jmedia.models

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.theme.themes.CosmicLatteDarkColorScheme
import fr.jaetan.jmedia.ui.theme.themes.CosmicLatteLightColorScheme
import fr.jaetan.jmedia.ui.theme.themes.NeonCityDarkColorScheme
import fr.jaetan.jmedia.ui.theme.themes.NeonCityLightColorScheme
import fr.jaetan.jmedia.ui.theme.themes.SunsetSiennaDarkColorScheme
import fr.jaetan.jmedia.ui.theme.themes.SunsetSiennaLightColorScheme
import fr.jaetan.jmedia.ui.theme.themes.SystemDarkColorScheme

enum class JColorScheme(@StringRes val title: Int, val shouldBeDisplayed: Boolean) {
    System(R.string.system, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S),
    CosmicLatte(R.string.cosmic_latte, true),
    SunsetSienna(R.string.sunset_sienna, true),
    NeonCity(R.string.neon_city, true);

    val colorScheme: ColorScheme
        @Composable
        get() {
            val context = LocalContext.current
            val isDarkTheme = when (MainViewModel.userSettings.currentTheme) {
                JTheme.Dark -> true
                JTheme.Light -> false
                JTheme.System -> isSystemInDarkTheme()
            }

            return when (this) {
                CosmicLatte -> if (isDarkTheme) CosmicLatteDarkColorScheme else CosmicLatteLightColorScheme
                SunsetSienna -> if (isDarkTheme) SunsetSiennaDarkColorScheme else SunsetSiennaLightColorScheme
                NeonCity -> if (isDarkTheme) NeonCityDarkColorScheme else NeonCityLightColorScheme
                System -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (isDarkTheme) SystemDarkColorScheme else dynamicLightColorScheme(context)
                } else {
                    if (isDarkTheme) NeonCityDarkColorScheme else NeonCityLightColorScheme
                }
            }
        }

    companion object {
        fun fromString(label: String?): JColorScheme = when (label) {
            CosmicLatte.name -> CosmicLatte
            SunsetSienna.name -> SunsetSienna
            NeonCity.name -> NeonCity
            System.name -> System
            else -> default
        }

        var default: JColorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            System
        } else {
            NeonCity
        }
    }
}