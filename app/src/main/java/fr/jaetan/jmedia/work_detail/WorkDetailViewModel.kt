package fr.jaetan.jmedia.work_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.core.extensions.isNotNull
import fr.jaetan.core.models.data.IRepository
import fr.jaetan.core.models.data.works.IWork
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.core.services.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkDetailViewModel(val workType: WorkType, private val workName: String): ViewModel() {
    var work by mutableStateOf(null as IWork?)
    var state by mutableStateOf(ListState.Loading)
    var isInLibrary by mutableStateOf(false)

    private val controller: IRepository = when (workType) {
        WorkType.Manga -> MainViewModel.mangaRepository
    }

    fun fetchManga() {
        state = ListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            work = controller.getOne(workName)
            state = if (work.isNotNull()) ListState.Data else ListState.Error
        }
    }

    fun likeHandler() {
        isInLibrary = !isInLibrary
    }
}