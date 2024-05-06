package fr.jaetan.jmedia.app.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.controllers.MediasManager
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    var showRemoveDataDialog by mutableStateOf(false)
        private set
    var isLoading by mutableStateOf(false)

    /**
     * Hide the **remove data** dialog
     */
    fun hideRemoveDataDialog() {
        if (!isLoading) {
            showRemoveDataDialog = false
        }
    }

    /**
     * Show the **remove data** dialog
     */
    fun showRemoveDataDialog() {
        showRemoveDataDialog = true
    }

    /**
     * Remove all app data
     */
    fun removeData(context: Context, mediasManager: MediasManager) {
        viewModelScope.launch {
            isLoading = true

            mediasManager.removeAll()
            MainViewModel.userSettings.clearUserPreferences(context)
            MainViewModel.restartApp(context)

            showRemoveDataDialog = false
            isLoading = false
        }
    }
}