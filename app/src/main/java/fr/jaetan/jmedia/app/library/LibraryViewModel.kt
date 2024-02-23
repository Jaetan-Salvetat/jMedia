package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.extensions.removeNullValues
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.services.MainViewModel

class LibraryViewModel(): ViewModel() {
    var isSearchBarActive by mutableStateOf(false)
    var bottomSheetWorkType by mutableStateOf(null as WorkType?)
    var searchValue by mutableStateOf("")

    val filteredWorks: List<IWork>
        get() = MainViewModel.worksController.allAsList.map {
            if (it.title.lowercase().contains(searchValue.lowercase())) it
            else null
        }.removeNullValues()
}