package fr.jaetan.jmedia.app.settings.appearance

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.theme.themes.JTheme
import kotlinx.coroutines.launch

class AppearanceViewModel: ViewModel() {
    fun setTheme(context: Context, theme: JTheme) {
        viewModelScope.launch {
            MainViewModel.userSettings.setTheme(context, theme)
        }
    }
}