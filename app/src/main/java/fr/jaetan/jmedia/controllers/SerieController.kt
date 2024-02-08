package fr.jaetan.jmedia.controllers

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.core.realm.entities.toSeries
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SerieController: IWorkController<Serie>() {
    override val fetchedWorks = mutableStateListOf<Serie>()
    override var localWorks = mutableStateListOf<Serie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(SerieApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            MainViewModel.serieRepository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toSeries())
                setLibraryValues()
            }
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { serie ->
            serie.copy(isInLibrary = isInLibrary(serie))
        }
    }

    override suspend fun libraryHandler(work: Serie) {
        var serie = SerieApi.getDetails(work.apiId)
        serie = serie.copy(id = work.id, title = work.title)

        Log.d("testt", "")

        fetchedWorks.replaceAll {
            if (it.id == work.id) serie
            else it
        }

        if (serie.isInLibrary) {
            localWorks.takeWhereEqualTo(serie)?.let {
                MainViewModel.serieRepository.remove(it.toBdd())
            }
            return
        }

        MainViewModel.serieRepository.add(serie.toBdd())
    }
}