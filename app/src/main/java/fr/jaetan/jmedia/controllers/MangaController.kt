package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.realm.entities.toMangas
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.toBdd

class MangaController: IWorkController<Manga>() {
    override val works = mutableStateListOf<Manga>()
    private var localMangas = mutableListOf<Manga>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(MangaApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localMangas.isNotEmpty()) return

        MainViewModel.mangaRepository.all.collect {
            localMangas.clear()
            localMangas.addAll(it.list.toMangas())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { manga ->
            manga.copy(isInLibrary = localMangas.find { it.title == manga.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Manga) {
        if (localMangas.find { it.title == work.title }.isNull()) {
            MainViewModel.mangaRepository.add(work.toBdd())
            return
        }

        localMangas.find { it.title == work.title }?.let {
            MainViewModel.mangaRepository.remove(it.toBdd())
        }
    }
}