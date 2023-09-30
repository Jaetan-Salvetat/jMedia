package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LibraryViewModel: ViewModel() {
    var showWorkTypeSelectorSheet by mutableStateOf(false)
}