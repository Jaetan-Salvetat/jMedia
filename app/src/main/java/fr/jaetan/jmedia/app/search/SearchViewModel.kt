package fr.jaetan.jmedia.app.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.app.search.controllers.MangaController
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.core.models.works.IWork
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.services.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    // Controllers
    private val mangaController = MangaController()

    // States
    var searchValue by mutableStateOf("")
    var listState by mutableStateOf(ListState.Default)
    var filters = MainViewModel.userSettingsModel.settings.selectedWorkTypes

    // Variables
    val implementedFilters = WorkType.all.filter { it.implemented }
    val works: List<IWork>
        get() = mangaController.mangas.sortedBy { it.title }
    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2 && filters.isNotEmpty()

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

    fun filterHandler(context: Context) {
        viewModelScope.launch {
            if (filters.size == implementedFilters.size) {
                MainViewModel.userSettingsModel.setWorkTypes(context, listOf())
                return@launch
            }

            MainViewModel.userSettingsModel.setWorkTypes(context, implementedFilters)
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
        }
    }
}
