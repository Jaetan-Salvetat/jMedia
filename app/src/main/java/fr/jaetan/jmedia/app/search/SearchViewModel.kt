package fr.jaetan.jmedia.app.search


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.medias.IMedia
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    private var _sort by mutableStateOf(Sort.Name)

    // States
    var searchValue by mutableStateOf("")
    var sortDirection by mutableStateOf(SortDirection.Ascending)
    var showSortMenu by mutableStateOf(false)
    val filters: List<MediaType>
        get() = MainViewModel.userSettings.selectedMediaTypes
    var sort: Sort
        get() = when {
            filters.size > 1 && _sort == Sort.Default -> Sort.Name
            else -> _sort
        }
        set(value) { _sort = value }

    // Variables
    val implementedFilters = MediaType.all.filter { it.implemented }
    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2 && filters.isNotEmpty()

    // Methods
    fun libraryHandler(media: IMedia) {
        viewModelScope.launch {
            MainViewModel.worksController.getController(media.type).libraryHandler(media)
        }
    }

    fun filterHandler(context: Context, action: suspend (filters: List<MediaType>?) -> Unit) {
        viewModelScope.launch {
            if (filters.size == implementedFilters.size) {
                MainViewModel.userSettings.setMediaTypes(context, listOf())
                return@launch
            }

            MainViewModel.userSettings.setMediaTypes(context, implementedFilters)
            action(filters.ifEmpty { null })
        }
    }

    fun filterHandler(context: Context, type: MediaType, action: suspend (filters: List<MediaType>?) -> Unit) {
        val localFilters = filters.toMutableList()
        var needSearch = false

        if (filters.contains(type)) {
            localFilters.remove(type)
        } else {
            localFilters.add(type)
            needSearch = true
        }

        viewModelScope.launch {
            MainViewModel.userSettings.setMediaTypes(context, localFilters)
            action(if (needSearch) filters else null)
        }
    }
}
