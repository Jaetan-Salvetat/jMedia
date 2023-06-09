package fr.jaetan.jmedia.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.core.models.data.IRepository
import fr.jaetan.core.models.data.works.IWork
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.core.services.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(val workType: WorkType): ViewModel() {
    var searchValue by mutableStateOf("")
    var state by mutableStateOf(ListState.Initial)
    var works = mutableStateListOf<IWork>()

    private var job: Job? = null
    private val controller: IRepository = when (workType) {
        WorkType.Manga -> MainViewModel.mangaRepository
    }

    fun searchWork() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            state = ListState.Loading

            works.clear()
            works.addAll(controller.getAll(searchValue))

            state = if (works.isEmpty()) ListState.Empty else ListState.Data
        }
    }

/*    private suspend fun convertImageUrlToBitmap(imageUrl: String): Bitmap? {
        return try {
            val inputStream = withContext(Dispatchers.IO) {
                URL(imageUrl).openStream()
            }
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }*/
}