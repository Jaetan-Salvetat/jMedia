package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.realm.entities.toMangas
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd

class MangaController: IWorkController<Manga>() {
    override val fetchedWorks = mutableStateListOf<Manga>()
    override var localWorks = mutableStateListOf<Manga>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(MangaApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        MainViewModel.mangaRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toMangas())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { manga ->
            manga.copy(isInLibrary = isInLibrary(manga))
        }
    }

    override suspend fun libraryHandler(work: Manga) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                MainViewModel.mangaRepository.remove(it.toBdd())
            }
            return
        }

        MainViewModel.mangaRepository.add(work.toBdd())
    }
}