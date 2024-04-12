package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.core.realm.entities.toSeries
import fr.jaetan.jmedia.core.realm.repositories.SerieRepository
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SerieController : IWorkController<Serie>() {
    private val repository by lazy { SerieRepository(MainViewModel.realm) }
    override var localWorks = mutableStateListOf<Serie>()

    override suspend fun fetch(searchValue: String): List<Serie> {
        return SerieApi.search(searchValue)
    }

    override suspend fun initializeFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toSeries())
                setLibraryValues()
            }
        }
    }

    override fun setLibraryValues() {
        /*fetchedWorks.replaceAll { serie ->
            serie.copy(isInLibrary = isInLibrary(serie))
        }*/
    }

    override suspend fun libraryHandler(work: Serie) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                repository.remove(it.toBdd())
            }
            return
        }

        addToLibrary(work)
    }

    private suspend fun addToLibrary(work: Serie) {
        var serie = SerieApi.getDetails(work.apiId)
        serie = serie.copy(id = work.id, title = work.title, isInLibrary = work.isInLibrary)

        /*fetchedWorks.replaceAll {
            if (it.id == work.id) serie
            else it
        }*/

        repository.add(serie.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}