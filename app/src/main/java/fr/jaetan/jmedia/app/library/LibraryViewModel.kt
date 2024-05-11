package fr.jaetan.jmedia.app.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {
    var currentTabIndex by mutableIntStateOf(0)
}