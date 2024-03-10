package fr.jaetan.jmedia.app.settings.appearance

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.models.JColorScheme
import fr.jaetan.jmedia.models.JTheme
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.launch

class AppearanceViewModel : ViewModel() {
    var showThemeSelectorMenu by mutableStateOf(false)

    fun setColorScheme(context: Context, colorScheme: JColorScheme) {
        viewModelScope.launch {
            MainViewModel.userSettings.setColorScheme(context, colorScheme)
        }
    }

    fun setTheme(context: Context, theme: JTheme) {
        viewModelScope.launch {
            MainViewModel.userSettings.setTheme(context, theme)
        }
    }

    fun setPurDark(context: Context, isPurDark: Boolean) {
        viewModelScope.launch {
            MainViewModel.userSettings.setPurDark(context, isPurDark)
        }
    }
}