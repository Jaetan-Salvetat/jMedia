package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.core.realm.entities.toSeries
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.toBdd

class SerieController: IWorkController<Serie>() {
    override val works = mutableStateListOf<Serie>()
    private val localSeries = mutableListOf<Serie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(SerieApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localSeries.isNotEmpty()) return

        MainViewModel.serieRepository.all.collect {
            localSeries.clear()
            localSeries.addAll(it.list.toSeries())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { serie ->
            serie.copy(isInLibrary = localSeries.find { it.title == serie.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Serie) {
        val serie = SerieApi.getDetails(work.apiId)

        if (localSeries.find { it.title == serie.title }.isNull()) {
            MainViewModel.serieRepository.add(serie.toBdd())
            return
        }

        localSeries.find { it.title == serie.title }?.let {
            MainViewModel.serieRepository.remove(it.toBdd())
        }
    }
}