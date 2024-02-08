package fr.jaetan.jmedia.controllers

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MangaApi
import fr.jaetan.jmedia.core.realm.entities.toMangas
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.equalTo
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
        if (localWorks.isNotEmpty()) return
        MainViewModel.mangaRepository.all.collect {
            Log.d("testt", "Controller")
            localWorks.clear()
            localWorks.addAll(it.list.toMangas())
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { manga ->
            manga.copy(isInLibrary = localWorks.find { manga.equalTo(it) }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Manga) {
        if (work.isInLibrary) {
            localWorks.find { it.title == work.title }?.let {
                MainViewModel.mangaRepository.remove(it.toBdd())
            }
        } else {
            MainViewModel.mangaRepository.add(work.toBdd())
        }

        setLibraryValues()
    }
}