package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.realm.entities.toMangas
import fr.jaetan.jmedia.core.realm.repositories.MangaRepository
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaController : IWorkController<Manga>() {
    private val repository by lazy { MangaRepository(MainViewModel.realm) }
    override var localWorks = mutableStateListOf<Manga>()

    override suspend fun fetch(searchValue: String): List<Manga> {
        return MangaApi.search(searchValue)
    }

    override suspend fun initializeFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toMangas())
                setLibraryValues()
            }
        }
    }

    override fun setLibraryValues() {
        /*fetchedWorks.replaceAll { manga ->
            manga.copy(isInLibrary = isInLibrary(manga))
        }*/
    }

    override suspend fun libraryHandler(work: Manga) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                repository.remove(it.toBdd())
            }
            return
        }

        repository.add(work.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}