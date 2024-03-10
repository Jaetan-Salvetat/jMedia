package fr.jaetan.jmedia.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import fr.jaetan.jmedia.R

enum class JTheme(@StringRes val title: Int, val icon: ImageVector) {
    Dark(R.string.dark, Icons.Default.LightMode),
    Light(R.string.light, Icons.Default.DarkMode),
    System(R.string.system, Icons.Default.Brightness6);

    companion object {
        fun fromString(label: String?): JTheme = when (label) {
            Dark.name -> Dark
            Light.name -> Light
            System.name -> System
            else -> System
        }
    }
}