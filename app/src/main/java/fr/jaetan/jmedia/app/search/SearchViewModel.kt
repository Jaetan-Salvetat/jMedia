package fr.jaetan.jmedia.app.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    var searchValue by mutableStateOf("")
    var searchBarIsActive by mutableStateOf(false)
}