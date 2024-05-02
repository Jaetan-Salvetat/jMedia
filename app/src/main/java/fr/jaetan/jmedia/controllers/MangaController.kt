package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.realm.entities.toMangas
import fr.jaetan.jmedia.core.realm.repositories.MangaRepository
import fr.jaetan.jmedia.models.medias.Manga
import fr.jaetan.jmedia.models.medias.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaController : IMediaController<Manga>() {
    private val repository by lazy { MangaRepository(MainViewModel.realm) }

    override suspend fun fetch(searchValue: String): List<Manga> {
        return MangaApi.search(searchValue)
    }

    override suspend fun initializeFlow(onDbChanged: (medias: List<Manga>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                onDbChanged(it.list.toMangas())
            }
        }
    }

    override suspend fun libraryHandler(media: Manga) {
        if (media.isInLibrary) {
//            localWorks.takeWhereEqualTo(media)?.let {
//                repository.remove(it.toBdd())
//            }
//            return
        }

        repository.add(media.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}