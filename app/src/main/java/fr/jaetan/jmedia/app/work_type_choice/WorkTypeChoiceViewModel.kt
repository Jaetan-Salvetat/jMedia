package fr.jaetan.jmedia.app.work_type_choice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.models.WorkType

class WorkTypeChoiceViewModel: ViewModel() {
    var showErrorSheet by mutableStateOf(false)

    fun goToLibrary(type: WorkType) {
        if (!type.implemented) {
            showErrorSheet = true
            return
        }
    }
}