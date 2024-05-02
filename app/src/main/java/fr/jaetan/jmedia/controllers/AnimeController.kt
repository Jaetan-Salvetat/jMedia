package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.core.networking.AnimeApi
import fr.jaetan.jmedia.core.realm.entities.toAnimes
import fr.jaetan.jmedia.core.realm.repositories.AnimeRepository
import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeController : IWorkController<Anime>() {
    private val repository by lazy { AnimeRepository(MainViewModel.realm) }

    override suspend fun fetch(searchValue: String): List<Anime> {
        return AnimeApi.search(searchValue)
    }

    override suspend fun initializeFlow(onDbChanged: (medias: List<Anime>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                onDbChanged(it.list.toAnimes())
            }
        }
    }

    override suspend fun libraryHandler(work: Anime) {
        if (work.isInLibrary) {
            repository.remove(work.toBdd())
            return
        }

        repository.add(work.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}