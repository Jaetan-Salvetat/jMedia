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

    fun hideRemoveDataDialog() {
        if (!isLoading) {
            showRemoveDataDialog = false
        }
    }

    fun showRemoveDataDialog() {
        showRemoveDataDialog = true
    }

    fun removeData(context: Context, mediasManager: MediasManager) {
        viewModelScope.launch {
            isLoading = true

            MainViewModel.clearUserData(context, mediasManager)

            showRemoveDataDialog = false
            isLoading = false
        }
    }
}