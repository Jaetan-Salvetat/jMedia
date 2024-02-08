package fr.jaetan.jmedia.app.search


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.removeNullValues
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    private var _sort by mutableStateOf(Sort.Name)

    // States
    var searchValue by mutableStateOf("")
    var listState by mutableStateOf(ListState.Default)
    var sortDirection by mutableStateOf(SortDirection.Ascending)
    var showSortMenu by mutableStateOf(false)
    val filters: List<WorkType>
        get() = MainViewModel.userSettingsModel.selectedWorkTypes
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
    val sortedWorks = mutableStateListOf<IWork>()

    // Methods
    fun fetchWorks(force: Boolean = true) {
        if (!searchIsEnabled) {
            return
        }

        viewModelScope.launch(dispatcher) {
            if (sortedWorks.isEmpty()) listState = ListState.Loading

            filters.forEach { type ->
                MainViewModel.getController(type).fetch(searchValue, force)
                sortWorks()
            }

            listState = when {
                sortedWorks.isNotEmpty() -> ListState.HasData
                else -> ListState.EmptyData
            }
        }
    }

    fun libraryHandler(work: IWork) {
        viewModelScope.launch {
            MainViewModel.getController(work.type).libraryHandler(work)
        }
    }

    fun filterHandler(context: Context) {
        viewModelScope.launch {
            if (filters.size == implementedFilters.size) {
                MainViewModel.userSettingsModel.setWorkTypes(context, listOf())
                return@launch
            }

            MainViewModel.userSettingsModel.setWorkTypes(context, implementedFilters)
            if (listState != ListState.Default) {
                fetchWorks(false)
            }
        }
    }

    fun filterHandler(context: Context, type: WorkType) {
        val localFilters = filters.toMutableList()

        if (filters.contains(type)) {
            localFilters.remove(type)
        } else {
            localFilters.add(type)
        }

        viewModelScope.launch {
            MainViewModel.userSettingsModel.setWorkTypes(context, localFilters)
            if (listState != ListState.Default) {
                fetchWorks(false)
            }
        }
    }

    private suspend fun sortWorks() = withContext(Dispatchers.IO) {
        val works = MainViewModel.controllersMap.toList().map {
            if (filters.contains(it.first)) it.second.fetchedWorks
            else null
        }.removeNullValues().flatMap { list -> list.map { it } }

        Log.d("testt", "sortWorks")

        sortedWorks.clear()
        sortedWorks.addAll(
            if (sortDirection == SortDirection.Ascending) {
                when(sort) {
                    Sort.Name -> works.sortedBy { it.title }
                    Sort.Rating -> works.sortedBy { it.rating }
                    Sort.Default -> works
                }
            } else {
                when(sort) {
                    Sort.Name -> works.sortedByDescending { it.title }
                    Sort.Rating -> works.sortedByDescending { it.rating }
                    Sort.Default -> works
                }
            }
        )
    }
}
