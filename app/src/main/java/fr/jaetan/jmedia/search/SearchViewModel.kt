package fr.jaetan.jmedia.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.core.models.ui.ListState

class SearchViewModel(val workType: WorkType): ViewModel() {
    var searchValue by mutableStateOf("")
    var state by mutableStateOf(ListState.Empty)
}