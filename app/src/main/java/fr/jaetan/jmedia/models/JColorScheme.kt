package fr.jaetan.jmedia.models

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
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

enum class JColorScheme(@StringRes val title: Int, val shouldBeDisplayed: Boolean) {
    System(R.string.system, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S),
    CosmicLatte(R.string.cosmic_latte, true),
    SunsetSienna(R.string.sunset_sienna, true),
    NeonCity(R.string.neon_city, true),
    Default(0, false);

    val colorScheme: ColorScheme
        @Composable
        get() {
            val isDarkTheme = when(MainViewModel.userSettings.currentTheme) {
                JTheme.Dark -> true
                JTheme.Light -> false
                JTheme.System -> isSystemInDarkTheme()
            }

            return when (this) {
                CosmicLatte -> if (isDarkTheme) CosmicLatteDarkColorScheme else CosmicLatteLightColorScheme
                SunsetSienna -> if (isDarkTheme) SunsetSiennaDarkColorScheme else SunsetSiennaLightColorScheme
                NeonCity -> if (isDarkTheme) NeonCityDarkColorScheme else NeonCityLightColorScheme
                System, Default -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val context = LocalContext.current
                    if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
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
            else -> Default
        }
    }
}