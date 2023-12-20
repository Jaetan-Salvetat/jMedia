package fr.jaetan.jmedia.app.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.core.extensions.isNull
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.models.works.toBdd
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.realm.entities.toMangas
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    var searchValue by mutableStateOf("")
    var works = mutableStateListOf<Manga>()
    var listState by mutableStateOf(ListState.Default)
    var filters = mutableStateListOf<WorkType>()
    var localWorks = mutableStateListOf<Manga>()

    init {
        viewModelScope.launch {
            MainViewModel.mangaRepository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toMangas())
            }
        }
    }

    val searchIsEnabled: Boolean
        get() = searchValue.length >= 2

    fun fetchWorks() {
        if (!searchIsEnabled) {
            return
        }

        viewModelScope.launch(dispatcher) {
            listState = ListState.Loading

            works.clear()
            works.addAll(urlImagesToBitmap(MangaApi.search(searchValue)))

            listState = if (works.isEmpty()) {
                ListState.EmptyData
            } else {
                ListState.HasData
            }
        }
    }

    private fun urlImagesToBitmap(works: List<Manga>): List<Manga> {
        val tempWorks = works

        for(index in works.indices) {
            try {
                val url = URL(works[index].image.imageUrl)
                val bitmap = BitmapFactory.decodeStream(url.openStream())
                tempWorks[index].image.bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    bitmap.width / 2,
                    bitmap.height / 2,
                    false
                )
            } catch (_: Exception) {}
        }

        return tempWorks
    }

    fun mangaLibraryHandler(manga: Manga) {
        viewModelScope.launch {
            if (localWorks.find { it.title == manga.title }.isNull()) {
                MainViewModel.mangaRepository.add(manga.toBdd())
            } else {
                localWorks.find { it.title == manga.title }?.let {
                    MainViewModel.mangaRepository.remove(it.toBdd())
                }
            }
        }
    }

    fun filterHandler() {
        if (filters.size == WorkType.all.size) {
            filters.clear()
            return
        }

        filters.clear()
        filters.addAll(WorkType.all)
    }

    fun filterHandler(type: WorkType) {
        if (filters.contains(type)) {
            filters.remove(type)
            return
        }

        filters.add(type)
    }
}