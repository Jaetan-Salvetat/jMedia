package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.core.networking.SerieApi
import fr.jaetan.jmedia.core.realm.entities.toSeries
import fr.jaetan.jmedia.core.realm.repositories.SerieRepository
import fr.jaetan.jmedia.models.medias.Serie
import fr.jaetan.jmedia.models.medias.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SerieController : IMediaController<Serie>() {
    private val repository by lazy { SerieRepository(MainViewModel.realm) }

    override suspend fun fetch(searchValue: String): List<Serie> {
        return SerieApi.search(searchValue)
    }

    override suspend fun initializeFlow(onDbChanged: (medias: List<Serie>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                onDbChanged(it.list.toSeries())
            }
        }
    }

    override suspend fun libraryHandler(media: Serie) {
        if (media.isInLibrary) {
//            localWorks.takeWhereEqualTo(media)?.let {
//                repository.remove(it.toBdd())
//            }
//            return
        }

        addToLibrary(media)
    }

    private suspend fun addToLibrary(media: Serie) {
        var serie = SerieApi.getDetails(media.apiId)
        serie = serie.copy(id = media.id, title = media.title, isInLibrary = media.isInLibrary)

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