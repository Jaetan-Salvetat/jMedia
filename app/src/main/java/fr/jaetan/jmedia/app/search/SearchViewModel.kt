package fr.jaetan.jmedia.app.search


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.controllers.AnimeController
import fr.jaetan.jmedia.controllers.BookController
import fr.jaetan.jmedia.controllers.IWorkController
import fr.jaetan.jmedia.controllers.MangaController
import fr.jaetan.jmedia.controllers.MovieController
import fr.jaetan.jmedia.controllers.SerieController
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


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    // Controllers
    private val controllers = mapOf(
        WorkType.Manga to MangaController(),
        WorkType.Anime to AnimeController(),
        WorkType.Book to BookController(),
        WorkType.Movie to MovieController(),
        WorkType.Serie to SerieController()
    )
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
    val works: List<IWork>
        get() = sortWorks()

    // Methods
    fun fetchWorks(force: Boolean = true) {
        if (!searchIsEnabled) {
            return
        }

        // Initialize works flow from local database
        if (listState == ListState.Default) {
            implementedFilters.forEach {
                viewModelScope.launch(dispatcher) { getController(it).initializeFlow() }
            }
        }

        viewModelScope.launch(dispatcher) {
            if (works.isEmpty()) listState = ListState.Loading

            filters.forEachIndexed { index, type ->
                getController(type).fetch(searchValue, force)
                updateListState(index == filters.size -1)
            }
        }
    }

    fun libraryHandler(work: IWork) {
        viewModelScope.launch {
            getController(work.type).libraryHandler(work)
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

    @Suppress("UNCHECKED_CAST")
    private fun getController(type: WorkType): IWorkController<IWork> = controllers[type] as IWorkController<IWork>

    // Private methods
    private fun updateListState(isLast: Boolean) {
        listState = when {
            works.isEmpty() && isLast -> ListState.EmptyData
            works.isNotEmpty() -> ListState.HasData
            else -> listState
        }
    }

    private fun sortWorks(): List<IWork> {
        val works = controllers.toList().map {
            if (filters.contains(it.first)) it.second.works
            else null
        }.removeNullValues().flatMap { list -> list.map { it } }

        return if (sortDirection == SortDirection.Ascending) {
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
    }
}
