package fr.jaetan.jmedia.ui.theme.themes

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.jaetan.jmedia.R

enum class JTheme(@StringRes val title: Int, val shouldBeDisplayed: Boolean) {
    System(R.string.system, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S),
    CosmicLatte(R.string.cosmic_latte, true),
    SunsetSienna(R.string.sunset_sienna, true),
    NeonCity(R.string.neon_city, true),
    Default(0, false);

    val colorScheme: ColorScheme
        @Composable
        get() = when (this) {
            CosmicLatte -> if (isSystemInDarkTheme()) CosmicLatteDarkColorScheme else CosmicLatteLightColorScheme
            SunsetSienna -> if (isSystemInDarkTheme()) SunsetSiennaDarkColorScheme else SunsetSiennaLightColorScheme
            NeonCity -> if (isSystemInDarkTheme()) NeonCityDarkColorScheme else NeonCityLightColorScheme
            System, Default -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (isSystemInDarkTheme()) NeonCityDarkColorScheme else NeonCityLightColorScheme
            }
        }

    companion object {
        fun fromString(label: String): JTheme = when (label) {
            CosmicLatte.name -> CosmicLatte
            SunsetSienna.name -> SunsetSienna
            NeonCity.name -> NeonCity
            System.name -> System
            else -> Default
        }
    }
}