package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.networking.MangaApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    var searchValue by mutableStateOf("")
    var works = mutableStateListOf<Manga>()
    var listState by mutableStateOf(ListState.None)

    fun fetchWorks(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            listState = ListState.Loading

            val works = MangaApi.search(searchValue)

            this@SearchViewModel.works.clear()
            this@SearchViewModel.works.addAll(works)

            listState = if (works.isEmpty()) {
                ListState.EmptyData
            } else {
                ListState.HasData
            }
        }
    }
}