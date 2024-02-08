package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.AnimeApi
import fr.jaetan.jmedia.core.realm.entities.toAnimes
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd

class AnimeController: IWorkController<Anime>() {
    override val fetchedWorks = mutableStateListOf<Anime>()
    override var localWorks = mutableStateListOf<Anime>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(AnimeApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        MainViewModel.animeRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toAnimes())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { manga ->
            manga.copy(isInLibrary = isInLibrary(manga))
        }
    }

    override suspend fun libraryHandler(work: Anime) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                MainViewModel.animeRepository.remove(it.toBdd())
            }
            return
        }

        MainViewModel.animeRepository.add(work.toBdd())
    }
}