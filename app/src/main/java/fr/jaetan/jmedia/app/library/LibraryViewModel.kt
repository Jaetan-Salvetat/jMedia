package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.extensions.isNull
import fr.jaetan.jmedia.core.services.MainViewModel

class LibraryViewModel: ViewModel() {
    var showWorkTypeSelectorSheet by mutableStateOf(false)
    var searchValue by mutableStateOf("")
    var searchBarIsActive by mutableStateOf(false)

    init {
        if (MainViewModel.state.currentWorkType.isNull()) {
            showWorkTypeSelectorSheet = true
        }
    }
}