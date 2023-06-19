package fr.jaetan.jmedia.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.core.models.data.WorkType

class LibraryViewModel(workType: WorkType): ViewModel() {
    var workType by mutableStateOf(workType)
    var showWorkMenu by mutableStateOf(false)

    fun toggleWorkType(workType: WorkType) {
        this.workType = workType
        showWorkMenu = false
    }
}