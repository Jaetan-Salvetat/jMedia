package fr.jaetan.jmedia.app.search


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.app.search.controllers.AnimeController
import fr.jaetan.jmedia.app.search.controllers.IWorkController
import fr.jaetan.jmedia.app.search.controllers.MangaController
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    // Controllers
    private val mangaController = MangaController()
    private val animeController = AnimeController()

    // States
    var searchValue by mutableStateOf("")
    var listState by mutableStateOf(ListState.Default)
    val filters: List<WorkType>
        get() = MainViewModel.userSettingsModel.selectedWorkTypes

    // Variables
    val implementedFilters = WorkType.all.filter { it.implemented }
    val works: List<IWork>
        get() {
            val works = mutableListOf<IWork>()

            implementedFilters.forEach {  type ->
                if (filters.contains(type)) {
                    getController(type)?.let {
                        works.addAll(it.works)
                    }
                }
            }

            return works.sortedBy { it.title }
        }
    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2 && filters.isNotEmpty()

    // Methods
    fun fetchWorks(force: Boolean = true) {
        if (!searchIsEnabled) {
            return
        }

        // Initialize works flow from local database
        if (listState == ListState.Default) {
            implementedFilters.forEach {
                viewModelScope.launch(dispatcher) { getController(it)?.initializeFlow() }
            }
        }

        viewModelScope.launch(dispatcher) {
            listState = ListState.Loading

            implementedFilters.forEach {
                if (filters.contains(it)) getController(it)?.fetch(searchValue, force)
            }

            listState = if (works.isEmpty()) {
                ListState.EmptyData
            } else {
                ListState.HasData
            }
        }
    }

    fun libraryHandler(work: IWork) {
        viewModelScope.launch {
            getController(work.type)?.libraryHandler(work)
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

    // Private methods
    @Suppress("UNCHECKED_CAST")
    private fun getController(type: WorkType): IWorkController<IWork>? = when (type) {
        WorkType.Manga -> mangaController as? IWorkController<IWork>
        WorkType.Anime -> animeController as? IWorkController<IWork>
        WorkType.Book -> TODO("Not implemented yet")
        WorkType.Serie -> TODO("Not implemented yet")
        WorkType.Movie -> TODO("Not implemented yet")
    }
}
