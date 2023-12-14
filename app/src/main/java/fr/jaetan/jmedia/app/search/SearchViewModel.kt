package fr.jaetan.jmedia.app.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.services.objectbox.MangaRepository
import fr.jaetan.jmedia.core.services.objectbox.converters.MangaEntityConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class SearchViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    var searchValue by mutableStateOf("")
    var works = mutableStateListOf<Manga>()
    var listState by mutableStateOf(ListState.Default)
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

    fun addToLibrary(work: Manga) {
        val mangaEntity = MangaEntityConverter.convertToEntityProperty(work)
        MangaRepository.addManga(mangaEntity)
    }
}