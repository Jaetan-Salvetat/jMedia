package fr.jaetan.jmedia.app.work_type_choice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.core.models.WorkType
import kotlinx.coroutines.launch

class WorkTypeChoiceViewModel(private val hide: suspend () -> Unit): ViewModel() {
    var showErrorSheet by mutableStateOf(false)

    fun setWorkType(type: WorkType) {
        if (!type.implemented) {
            showErrorSheet = true
            return
        }

        viewModelScope.launch { hide() }
    }
}