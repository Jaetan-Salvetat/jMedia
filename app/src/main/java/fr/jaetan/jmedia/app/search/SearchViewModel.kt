package fr.jaetan.jmedia.app.search


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    private var _sort by mutableStateOf(Sort.Name)

    // States
    var searchValue by mutableStateOf("")
    var sortDirection by mutableStateOf(SortDirection.Ascending)
    var showSortMenu by mutableStateOf(false)
    val filters: List<WorkType>
        get() = MainViewModel.userSettings.selectedWorkTypes
    var sort: Sort
        get() = when {
            filters.size > 1 && _sort == Sort.Default -> Sort.Name
            else -> _sort
        }
        set(value) { _sort = value }

    // Variables
    val implementedFilters = WorkType.all.filter { it.implemented }
    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2 && filters.isNotEmpty()

    // Methods
    fun libraryHandler(work: IWork) {
        viewModelScope.launch {
            MainViewModel.worksController.getController(work.type).libraryHandler(work)
        }
    }

    fun filterHandler(context: Context, action: suspend (filters: List<WorkType>?) -> Unit) {
        viewModelScope.launch {
            if (filters.size == implementedFilters.size) {
                MainViewModel.userSettings.setWorkTypes(context, listOf())
                return@launch
            }

            MainViewModel.userSettings.setWorkTypes(context, implementedFilters)
            action(filters.ifEmpty { null })
        }
    }

    fun filterHandler(context: Context, type: WorkType, action: suspend (filters: List<WorkType>?) -> Unit) {
        val localFilters = filters.toMutableList()
        var needSearch = false

        if (filters.contains(type)) {
            localFilters.remove(type)
        } else {
            localFilters.add(type)
            needSearch = true
        }

        viewModelScope.launch {
            MainViewModel.userSettings.setWorkTypes(context, localFilters)
            action(if (needSearch) filters else null)
        }
    }
}
