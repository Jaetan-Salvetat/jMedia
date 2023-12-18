package fr.jaetan.jmedia.core.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.models.WorkType

class StateViewModel: ViewModel() {
    var currentWorkType by mutableStateOf(WorkType.Manga as WorkType?)
}