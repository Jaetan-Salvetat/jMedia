package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.core.realm.entities.toSeries
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd

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
        if (localWorks.isNotEmpty()) return

        MainViewModel.serieRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toSeries())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { serie ->
            serie.copy(isInLibrary = isInLibrary(serie))
        }
    }

    override suspend fun libraryHandler(work: Serie) {
        val serie = SerieApi.getDetails(work.apiId)

        if (!work.isInLibrary) {
            MainViewModel.serieRepository.add(serie.toBdd())
            return
        }

        localWorks.takeWhereEqualTo(serie)?.let {
            MainViewModel.serieRepository.remove(it.toBdd())
        }
    }
}