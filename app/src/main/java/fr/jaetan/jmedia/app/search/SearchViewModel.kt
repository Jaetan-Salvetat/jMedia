package fr.jaetan.jmedia.app.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.app.search.controllers.AnimeController
import fr.jaetan.jmedia.app.search.controllers.MangaController
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.Manga
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

            if (filters.contains(WorkType.Manga)) works.addAll(mangaController.mangas)
            if (filters.contains(WorkType.Anime)) works.addAll(animeController.animes)

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
            viewModelScope.launch(dispatcher) { mangaController.initializeFlow() }
            viewModelScope.launch(dispatcher) { animeController.initializeFlow() }
        }

        viewModelScope.launch(dispatcher) {
            listState = ListState.Loading

            // Mangas handler
            if (filters.contains(WorkType.Manga)) {
                mangaController.fetch(searchValue, force)
            }
            if (filters.contains(WorkType.Anime)) {
                animeController.fetch(searchValue, force)
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
                is Anime -> animeController.libraryHandler(work)
            }
        }
    }

    fun filterHandler(context: Context) {
        viewModelScope.launch {
            if (filters.size == implementedFilters.size) {
                MainViewModel.userSettingsModel.setWorkTypes(context, listOf())
                return@launch
            }

            MainViewModel.userSettingsModel.setWorkTypes(context, implementedFilters)
            // TODO: Do not start the research if works are empty
            fetchWorks(false)
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
            // TODO: Do not start the research if works are empty
            fetchWorks(false)
        }
    }
}
