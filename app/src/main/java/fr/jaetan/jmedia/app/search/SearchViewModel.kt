package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.app.search.controllers.MangaController
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.core.models.works.IWork
import fr.jaetan.jmedia.core.models.works.Manga
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    // Controllers
    val mangaController = MangaController()

    // States
    var searchValue by mutableStateOf("")
    var listState by mutableStateOf(ListState.Default)
    var filters = mutableStateListOf<WorkType>()

    // Variables
    val implementedFilters = WorkType.all.filter { it.implemented }
    val works: List<IWork>
        get() = mangaController.mangas.sortedBy { it.title }
    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2

    fun fetchWorks() {
        if (!searchIsEnabled) {
            return
        }

        // Initialize works flow from local database
        viewModelScope.launch { mangaController.initializeFlow() }

        viewModelScope.launch(dispatcher) {
            listState = ListState.Loading

            // Mangas handler
            if (filters.contains(WorkType.Manga)) {
                mangaController.fetch(searchValue)
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
            when (work) {
                is Manga -> mangaController.libraryHandler(work)
            }
        }
    }

    fun filterHandler() {
        if (filters.size == implementedFilters.size) {
            filters.clear()
            return
        }

        filters.clear()
        filters.addAll(implementedFilters)
    }

    fun filterHandler(type: WorkType) {
        if (filters.contains(type)) {
            filters.remove(type)
            return
        }

        filters.add(type)
    }
}
