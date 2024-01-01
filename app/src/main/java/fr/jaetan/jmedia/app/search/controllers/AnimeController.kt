package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.AnimeApi
import fr.jaetan.jmedia.models.works.Anime

class AnimeController: IWorkController<Anime>() {
    val animes = mutableStateListOf<Anime>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && animes.isNotEmpty()) return
        animes.clear()
        animes.addAll(generateBitmaps(AnimeApi.search(searchValue)))
    }

    override suspend fun initializeFlow() {
        TODO("Not yet implemented")
    }

    override fun setLibraryValues() {
        TODO("Not yet implemented")
    }

    override suspend fun libraryHandler(work: Anime) {
        TODO("Not yet implemented")
    }
}