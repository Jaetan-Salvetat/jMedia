package fr.jaetan.jmedia.ui.theme.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import fr.jaetan.jmedia.services.MainViewModel

val CosmicLatteLightColorScheme = lightColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF3700B3),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF000000),
    inversePrimary = Color(0xFF6750A4),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF000000),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF000000),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    surfaceTint = Color(0xFFD0BCFF),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    outline = Color(0xFF84746E),
    outlineVariant = Color(0xFFF4DED9),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFFFFFBFE),
    surfaceDim = Color(0xFFF5ECE7),
    surfaceContainer = Color(0xFFFFFBFE),
    surfaceContainerHigh = Color(0xFFF6E2DA),
    surfaceContainerHighest = Color(0xFFF9E8E0),
    surfaceContainerLow = Color(0xFFE7DAD1),
    surfaceContainerLowest = Color(0xFFE0D0C8)
)

val CosmicLatteDarkColorScheme: ColorScheme
    get() = darkColorScheme(
        primary = Color(0xFF6750A4),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFEADDFF),
        onPrimaryContainer = Color(0xFF21005D),
        inversePrimary = Color(0xFFD0BCFF),
        secondary = Color(0xFF625B71),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFE8DEF8),
        onSecondaryContainer = Color(0xFF1D192B),
        tertiary = Color(0xFF7D5260),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFFFD8E4),
        onTertiaryContainer = Color(0xFF31111D),
        background = if (MainViewModel.userSettings.isPureDark) Color.Black else Color(0xFF1C1B1F),
        onBackground = Color(0xFFE6E1E5),
        surface = Color(0xFF1C1B1F),
        onSurface = Color(0xFFE6E1E5),
        surfaceVariant = Color(0xFF49454F),
        onSurfaceVariant = Color(0xFFCAC4D0),
        surfaceTint = Color(0xFF6750A4),
        inverseSurface = Color(0xFF313033),
        inverseOnSurface = Color(0xFFF4EFF4),
        error = Color(0xFFB3261E),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFF9DEDC),
        onErrorContainer = Color(0xFF410E0B),
        outline = Color(0xFF938F99),
        outlineVariant = Color(0xFFE7E0EC),
        scrim = Color(0xFF000000),
        surfaceBright = Color(0xFF262529),
        surfaceDim = Color(0xFF18171C),
        surfaceContainer = Color(0xFF262529),
        surfaceContainerHigh = Color(0xFF3D3B41),
        surfaceContainerHighest = Color(0xFF4E4C52),
        surfaceContainerLow = Color(0xFF1F1D22),
        surfaceContainerLowest = Color(0xFF171519)
    )