package fr.jaetan.jmedia.app.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    var showRemoveDataDialog by mutableStateOf(false)

    fun removeData() {
        showRemoveDataDialog = false
    }
}