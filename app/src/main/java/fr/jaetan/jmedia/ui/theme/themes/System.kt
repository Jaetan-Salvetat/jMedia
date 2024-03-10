package fr.jaetan.jmedia.ui.theme.themes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import fr.jaetan.jmedia.services.MainViewModel

val SystemDarkColorScheme: ColorScheme
    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    get() {
        val context = LocalContext.current
        val dynamicDark = dynamicDarkColorScheme(context)

        return darkColorScheme(
            primary = dynamicDark.primary,
            onPrimary = dynamicDark.onPrimary,
            primaryContainer = dynamicDark.primaryContainer,
            onPrimaryContainer = dynamicDark.onPrimaryContainer,
            inversePrimary = dynamicDark.inversePrimary,
            secondary = dynamicDark.secondary,
            onSecondary = dynamicDark.onSecondary,
            secondaryContainer = dynamicDark.secondaryContainer,
            onSecondaryContainer = dynamicDark.onSecondaryContainer,
            tertiary = dynamicDark.tertiary,
            onTertiary = dynamicDark.onTertiary,
            tertiaryContainer = dynamicDark.onTertiaryContainer,
            onTertiaryContainer = dynamicDark.onTertiaryContainer,
            background = if (MainViewModel.userSettings.isPureDark) Color.Black else dynamicDark.background,
            onBackground = dynamicDark.onBackground,
            surface = if (MainViewModel.userSettings.isPureDark) Color.Black else dynamicDark.surface,
            onSurface = dynamicDark.onSurface,
            surfaceVariant = dynamicDark.surfaceVariant,
            onSurfaceVariant = dynamicDark.onSurfaceVariant,
            surfaceTint = dynamicDark.surfaceTint,
            inverseSurface = dynamicDark.inverseSurface,
            inverseOnSurface = dynamicDark.inverseOnSurface,
            error = dynamicDark.error,
            onError = dynamicDark.onError,
            errorContainer = dynamicDark.errorContainer,
            onErrorContainer = dynamicDark.onErrorContainer,
            outline = dynamicDark.outline,
            outlineVariant = dynamicDark.outlineVariant,
            scrim = dynamicDark.scrim,
            surfaceBright = dynamicDark.surfaceBright,
            surfaceDim = dynamicDark.surfaceDim,
            surfaceContainer = dynamicDark.surfaceContainer,
            surfaceContainerHigh = dynamicDark.surfaceContainerHigh,
            surfaceContainerHighest = dynamicDark.surfaceContainerHighest,
            surfaceContainerLow = dynamicDark.surfaceContainerLow,
            surfaceContainerLowest = dynamicDark.surfaceContainerLowest
        )
    }