package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.AnimeApi
import fr.jaetan.jmedia.core.realm.entities.toAnimes
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.toBdd

class AnimeController: IWorkController<Anime>() {
    override val works = mutableStateListOf<Anime>()
    override var localWorks = mutableStateListOf<Anime>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(AnimeApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localWorks.isNotEmpty()) return

        MainViewModel.animeRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toAnimes())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { anime ->
            anime.copy(isInLibrary = localWorks.find { it.title == anime.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Anime) {
        if (localWorks.find { it.title == work.title }.isNull()) {
            MainViewModel.animeRepository.add(work.toBdd())
            return
        }

        localWorks.find { it.title == work.title }?.let {
            MainViewModel.animeRepository.remove(it.toBdd())
        }
    }
}